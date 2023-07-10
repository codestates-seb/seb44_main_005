package actiOn.Img.service;


import actiOn.Img.profileImg.ProfileImg;
import actiOn.Img.profileImg.ProfileImgRepository;
import actiOn.Img.storeImg.StoreImg;
import actiOn.Img.storeImg.StoreImgRepository;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import actiOn.store.service.StoreService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ImgService {
    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_NAME;

    @Value("${cloud.aws.region.static}")
    private String REGION;

    //    private final String S3Repository = "https://test-main-005.s3.ap-northeast-2.amazonaws.com/";
    private final ProfileImgRepository profileImgRepository;
    private final StoreImgRepository storeImgRepository;
    private final StoreRepository storeRepository;

    public ImgService(ProfileImgRepository profileImgRepository, StoreImgRepository storeImgRepository,
                      StoreRepository storeRepository) {
        this.profileImgRepository = profileImgRepository;
        this.storeImgRepository = storeImgRepository;
        this.storeRepository = storeRepository;
    }

    // 기본 프로필 이미지 경로 저장하는 메서드
    public ProfileImg createDefaultProfileImg(Member member) {
        ProfileImg profileImg = new ProfileImg();
        profileImg.setLink("default Link"); // TODO 변경 해주어야 함
        profileImg.setMember(member);

        return profileImg;
    }

    public static String generateRandomName(){
        String uuid = UUID.randomUUID().toString();
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 25;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return uuid+generatedString;
    }

    public String uploadImage(MultipartFile file, String imageName) throws IOException {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION)
                .build();

        s3Client.putObject(new PutObjectRequest(BUCKET_NAME, imageName, file.getInputStream(), null));

        String fileUrl = s3Client.getUrl(BUCKET_NAME, imageName).toString();
        return fileUrl;
    }

    public void uploadProfileImage(MultipartFile file, Member member) {
        try {
            String imageName = "test";
            String fileUrl = uploadImage(file, imageName);

            ProfileImg profileImg = new ProfileImg();
            profileImg.setLink(fileUrl);
            profileImg.setMember(member);
            profileImgRepository.save(profileImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<StoreImg> uploadStoreImage(List<MultipartFile> files, long storeId) {
        String randomString = generateRandomName();
        try {
            int index=1;
            List<StoreImg> storeImgs = new ArrayList<>();
            for (MultipartFile file : files) {

                String imageName = String.valueOf(storeId) +  randomString + String.valueOf(index);
                StoreImg storeImg = new StoreImg();
                storeImg.setStore(storeRepository.findById(storeId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND)));
                String fileUrl = uploadImage(file, imageName);
                storeImg.setLink(fileUrl);
                if (file.equals(files.get(0))){
                    storeImg.setIsThumbnail(true);
                }
                storeImgs.add(storeImgRepository.save(storeImg));
                index++;
            }
            return storeImgs;
            //Todo url / 디비에 저장
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<ProfileImg> findProfileImgByMember(Member member) {
        return profileImgRepository.findByMember(member);
    }

    public Optional<StoreImg> findStoreImgByStore(Store store) {
        return storeImgRepository.findByStore(store);
    }

    public void deleteProfileImg(Member member) {
        //Todo 진짜 삭제하지 않고 기본 프로필로 바꾸는 작업
    }

    public void deleteStoreImg(Store store) {  // 스토어 이미지들 삭제

    }
}
