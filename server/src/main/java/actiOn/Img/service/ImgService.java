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
import lombok.AllArgsConstructor;
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

    public ImgService (ProfileImgRepository profileImgRepository, StoreImgRepository storeImgRepository){
        this.profileImgRepository = profileImgRepository;
        this.storeImgRepository = storeImgRepository;
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

    public StoreImg StoreThumbnailImgIdGenerator(Store store, StoreImg storeImg){
        storeImg.setIsThumbnail(true);
        StoreImg thumbnailImg = storeImgRepository.findByStoreAndIsThumbnail(store, true);
        if (thumbnailImg != null) storeImg.setImgId(thumbnailImg.getImgId());
        return storeImg;
    }
    public void uploadStoreImage(List<MultipartFile> files, Store store, MultipartFile thumbnailImage) throws IOException {
        String randomStringForImageName = generateRandomName();
        List<StoreImg> storeImgs = new ArrayList<>();
        int index=1;
        for (MultipartFile file : files) {
            if (file==null)continue;
            String imageName = String.valueOf(store.getStoreId()) + randomStringForImageName + String.valueOf(index);
            String fileUrl = uploadImage(file, imageName);
            StoreImg storeImg = new StoreImg(fileUrl, store);
            if (file.equals(thumbnailImage)) storeImg = StoreThumbnailImgIdGenerator(store,storeImg);
            storeImgs.add(storeImg);
            index++;
            }
        storeImgRepository.saveAll(storeImgs);
        }//수정완료

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
