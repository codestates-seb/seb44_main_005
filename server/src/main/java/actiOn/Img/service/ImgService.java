package actiOn.Img.service;


import actiOn.Img.profileImg.ProfileImg;
import actiOn.Img.profileImg.ProfileImgRepository;
import actiOn.Img.storeImg.StoreImg;
import actiOn.Img.storeImg.StoreImgRepository;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
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

    public ImgService(ProfileImgRepository profileImgRepository, StoreImgRepository storeImgRepository) {
        this.profileImgRepository = profileImgRepository;
        this.storeImgRepository = storeImgRepository;
    }

    // 기본 프로필 이미지 설정
    public ProfileImg setDefaultProfileImage(Member member) {
        ProfileImg profileImg = new ProfileImg();
        profileImg.setMember(member);

        return profileImgRepository.save(profileImg);
    }

    // 프로필 이미지 등록
    @Transactional
    public ProfileImg uploadProfileImage(MultipartFile file, Member member) throws IOException {
        // 기존 프로필 사진이 존재하는 경우
        if (existsCurrentProfileImage(member)) {
            // 기존 프로필 DELETED로 변경
            updateProfileImageStatusDeleted(member);
        }


        // S3에 이미지 파일 업로드
        String imageName = generateRandomName();
        String fileUrl = uploadImage(file, imageName);

        // 새로운 프로필 이미지 생성
        ProfileImg profileImg = createNewProfileImage(member, fileUrl);
        return profileImgRepository.save(profileImg);
    }

    // 기본 프로필 이미지 탐색
    public ProfileImg getDefaultProfileImage(Member member) {
        Optional<ProfileImg> defaultProfileImg = profileImgRepository.findByMemberAndImgStatus(
                member, ProfileImg.ProfileImgStatus.PROFILE_DEFAULT);

        if (defaultProfileImg.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.PROFILE_IMAGE_NOT_FOUND);
        }

        return defaultProfileImg.get();
    }

    // 기존 프로필 이미지 status DELETED로 변경
    public void updateProfileImageStatusDeleted(Member member) {
        ProfileImg currentProfileImg = findProfileImgByMember(member);
        currentProfileImg.setImgStatus(ProfileImg.ProfileImgStatus.PROFILE_DELETED);

        profileImgRepository.save(currentProfileImg);
    }

    // 업체 이미지 등록
    @Transactional
    public void uploadStoreImage(List<MultipartFile> files, Store store, MultipartFile thumbnailImage) throws IOException {
        int storeImgListSize = storeImgRepository.countByStore(store);

        if (files.size() > 12 - storeImgListSize) { // 사진개수 제한
            int remainingSize = 12 - storeImgListSize;
            if (remainingSize < 0) remainingSize = 0;
            files = files.subList(0, remainingSize);
        }

        String randomStringForImageName = generateRandomName();
        List<StoreImg> storeImgs = new ArrayList<>();

        int index = 1;
        for (MultipartFile file : files) {
            if (file == null) {
                continue;
            }

            String imageName = store.getStoreId() + randomStringForImageName + index;
            String fileUrl = uploadImage(file, imageName);
            StoreImg storeImg = new StoreImg(fileUrl, store);

            if (file.equals(thumbnailImage)) {
                storeImg = StoreThumbnailImgIdGenerator(store, storeImg);
            }
            storeImgs.add(storeImg);
            index++;
        }
        storeImgRepository.saveAll(storeImgs);
    }

    // 새로운 프로필 이미지 생성
    private ProfileImg createNewProfileImage(Member member, String fileUrl) {
        ProfileImg profileImg = new ProfileImg();
        profileImg.setLink(fileUrl);
        profileImg.setImgStatus(ProfileImg.ProfileImgStatus.PROFILE_ACTIVE);
        profileImg.setMember(member);

        return profileImg;
    }

    // 기존 프로필 사진 탐색
    private ProfileImg findProfileImgByMember(Member member) {
        Optional<ProfileImg> currentProfileImg = profileImgRepository
                .findByMemberAndImgStatus(member, ProfileImg.ProfileImgStatus.PROFILE_ACTIVE);

        if (currentProfileImg.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.PROFILE_IMAGE_NOT_FOUND);
        }
        return currentProfileImg.get();
    }

    // 기존 프로필 사진이 존재하는지 확인
    private boolean existsCurrentProfileImage(Member member) {
        return profileImgRepository
                .findByMemberAndImgStatus(member, ProfileImg.ProfileImgStatus.PROFILE_ACTIVE)
                .isPresent();
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

    private StoreImg StoreThumbnailImgIdGenerator(Store store, StoreImg storeImg) {
        storeImg.setIsThumbnail(true);
        StoreImg thumbnailImg = storeImgRepository.findByStoreAndIsThumbnail(store, true);
        if (thumbnailImg != null) storeImg.setImgId(thumbnailImg.getImgId());
        return storeImg;
    }

    public void deleteStoreImage(String link) {
        storeImgRepository.deleteByLink(link.replace(" ", ""));
    }
}
