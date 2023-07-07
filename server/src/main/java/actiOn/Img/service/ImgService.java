package actiOn.Img.service;


import actiOn.Img.profileImg.ProfileImg;
import actiOn.Img.profileImg.ProfileImgRepository;
import actiOn.Img.storeImg.StoreImg;
import actiOn.Img.storeImg.StoreImgRepository;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImgService {
    private final String S3Repository = "https://test-main-005.s3.ap-northeast-2.amazonaws.com/";
    private final ProfileImgRepository profileImgRepository;
    private final StoreImgRepository storeImgRepository;
    public ImgService(ProfileImgRepository profileImgRepository, StoreImgRepository storeImgRepository){
        this.profileImgRepository = profileImgRepository;
        this.storeImgRepository = storeImgRepository;
    }
    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_NAME;
    @Value("${cloud.aws.region.static}")
    private String REGION;

    public void uploadProfileImage(MultipartFile file, String fileName, Member member) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(REGION)
                    .build();
//            String fileName = file.getOriginalFilename();

            s3Client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, file.getInputStream(), null));

            String fileUrl = s3Client.getUrl(BUCKET_NAME, fileName).toString();
            ProfileImg profileImg = new ProfileImg();
            profileImg.setLink(fileName);
            profileImg.setMember(member);
            profileImgRepository.save(profileImg);
            //Todo url / 디비에 저장
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadStoreImage(MultipartFile file, String fileName, Store store) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(REGION)
                    .build();
//            String fileName = file.getOriginalFilename();

            s3Client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, file.getInputStream(), null));

            String fileUrl = s3Client.getUrl(BUCKET_NAME, fileName).toString();
            StoreImg storeImg = new StoreImg();
            storeImg.setLink(fileName);
            storeImg.setStore(store);
            storeImgRepository.save(storeImg);
            //Todo url / 디비에 저장
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<ProfileImg> findProfileImgByMember(Member member){
        return profileImgRepository.findByMember(member);
    }

    public Optional<StoreImg> findStoreImgByStore(Store store){
        return storeImgRepository.findByStore(store);
    }


    public void deleteProfileImg(Member member){
        //Todo 진짜 삭제하지 않고 기본 프로필로 바꾸는 작업
    }

    public void deleteStoreImg(Store store){  // 스토어 이미지들 삭제

    }
}
