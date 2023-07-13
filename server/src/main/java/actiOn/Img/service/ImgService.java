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
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ProfileImg setDefaultProfileImg(Member member) {
        ProfileImg profileImg = new ProfileImg();
        profileImg.setLink("default Link"); // TODO 변경 해주어야 함
        profileImg.setMember(member);

        return profileImg;
    }

    // 기존 프로필 이미지 status DELETED로 변경
    public ProfileImg updateCurrentProfileImageStatus(Member member) {
        ProfileImg currentProfileImg = findProfileImgByMember(member);
        currentProfileImg.setImgStatus(ProfileImg.ProfileImgStatus.PROFILE_DELETED);

        return profileImgRepository.save(currentProfileImg);
    }

    // 프로필 이미지 등록
    public ProfileImg uploadProfileImage(MultipartFile file, Member member) throws IOException {
        String imageName = generateRandomName();
        String fileUrl = uploadImage(file, imageName);

        ProfileImg profileImg = new ProfileImg();
        profileImg.setLink(fileUrl);
        profileImg.setMember(member);
        return profileImgRepository.save(profileImg);
    }

    public List<StoreImg> uploadStoreImage(List<MultipartFile> files, long storeId, MultipartFile thumbnailImage) {
        String randomString = generateRandomName();
        try {
            List<StoreImg> storeImgs = new ArrayList<>();
            if (thumbnailImage != null) { //thumbnailImage가 null이 아니면 파일리스트 0번째 넣어서 썸네일로 만들기
                files.add(0,thumbnailImage);
            }
            else{
                files.add(0,null);
            }

            Store findStore = storeRepository.findById(storeId).orElseThrow(()->new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));
            if (findStore == null) {
                return null;
            }
            int index=1;
            for (MultipartFile file : files) {
                if (file != null){
                    String imageName = String.valueOf(storeId) +  randomString + String.valueOf(index);
                    StoreImg storeImg = new StoreImg();
                    String fileUrl = uploadImage(file, imageName);
                    storeImg.setLink(fileUrl);
                    if (file.equals(files.get(0))){
                        storeImg.setIsThumbnail(true);
                        StoreImg findThumbnail = storeImgRepository.findByStoreAndIsThumbnail(findStore, true);
                        if (findThumbnail != null) {
                            storeImg.setImgId(findThumbnail.getImgId());
                        }
                    }
                    storeImg.setStore(findStore);
                    storeImgs.add(storeImgRepository.save(storeImg));
                    index++;
                }
            }

            return storeImgs;
            //Todo url / 디비에 저장
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private ProfileImg findProfileImgByMember(Member member) {
        Optional<ProfileImg> profileImg = profileImgRepository.findByMember(member);

        if (profileImg.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.PROFILE_IMAGE_NOT_FOUND);
        }
        return profileImg.get();
    }

    public Optional<StoreImg> findStoreImgByStore(Store store) {
        return storeImgRepository.findByStore(store);
    }

    public void deleteStoreImg(Store store) {  // 스토어 이미지들 삭제

    }

    private String uploadImage(MultipartFile file, String imageName) throws IOException {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION)
                .build();

        s3Client.putObject(new PutObjectRequest(
                BUCKET_NAME, imageName, file.getInputStream(), null)
        );

        String fileUrl = s3Client.getUrl(BUCKET_NAME, imageName).toString();
        return fileUrl;
    }

    private String generateRandomName() {
        String uuid = UUID.randomUUID().toString();
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 25;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return uuid + generatedString;
    }
}
