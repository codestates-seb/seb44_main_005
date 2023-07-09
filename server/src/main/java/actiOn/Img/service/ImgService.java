package actiOn.Img.service;


import actiOn.Img.profileImg.ProfileImg;
import actiOn.Img.profileImg.ProfileImgRepository;
import actiOn.Img.storeImg.StoreImg;
import actiOn.Img.storeImg.StoreImgRepository;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import actiOn.store.service.StoreService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class ImgService {

//    private final String S3Repository = "https://test-main-005.s3.ap-northeast-2.amazonaws.com/";
    private final ProfileImgRepository profileImgRepository;
    private final StoreImgRepository storeImgRepository;
    private final StoreService storeService;
    public ImgService(ProfileImgRepository profileImgRepository, StoreImgRepository storeImgRepository,
                      StoreService storeService){
        this.profileImgRepository = profileImgRepository;
        this.storeImgRepository = storeImgRepository;
        this.storeService = storeService;
    }
    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_NAME;
    @Value("${cloud.aws.region.static}")
    private String REGION;

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
    public void uploadProfileImage(MultipartFile file,Member member) {
        try {
            //Todo 프로필이미지 디비에 해당 멤버가 있는지 확인
            ProfileImg findProfileImg = checkProfileImgByMember(member);
            String imageName;
            //프로필이 이미 있으면 이미 있는 이름으로 업로드(덮어쓰기), 없으면 생성
            if (findProfileImg== null){
                imageName = generateRandomName();
            }
            else {
                imageName = findProfileImg.getLink();
            }

            String fileUrl = uploadImage(file,imageName);

            ProfileImg profileImg = new ProfileImg();
            profileImg.setLink(fileUrl);
            profileImg.setMember(member);
            profileImgRepository.save(profileImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadStoreImage(List<MultipartFile> files, long storeId) {
        Optional<Store> optionalStore = storeService.findStoreByStoreId(storeId);
        if (optionalStore.isPresent()) {
            Store targetStore = optionalStore.get();
            try {

                for (MultipartFile file : files) {
                    String imageName;
                    do {
                        imageName = generateRandomName();
                    } while (checkStoreImgByLink(imageName));

                    uploadImage(file, imageName);
                    StoreImg storeImg = new StoreImg();
                    storeImg.setLink(imageName);
                    storeImg.setStore(targetStore);
                    storeImgRepository.save(storeImg);
                }


            } catch (Exception e) {
                e.printStackTrace();
                //Todo 이미지 저장에 실패하면 스토어 등록된거 삭제되도록 해야함
            }
        } else throw new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND);
    }
        public Optional<ProfileImg> findProfileImgByMember (Member member){
            return profileImgRepository.findByMember(member);
        }

        public Optional<StoreImg> findStoreImgByStore (Store store){
            return storeImgRepository.findByStore(store);
        }

        public boolean checkStoreImgByLink(String link) {
            return storeImgRepository.findByLink(link) != null;
        }

        public ProfileImg checkProfileImgByMember(Member member) {
            return profileImgRepository.findByMember(member).orElse(null);
        }
        public void deleteProfileImg (Member member){
            //Todo 진짜 삭제하지 않고 기본 프로필로 바꾸는 작업
        }

        public void deleteStoreImg (Store store){  // 스토어 이미지들 삭제

        }
    }