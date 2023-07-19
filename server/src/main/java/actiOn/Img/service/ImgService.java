package actiOn.Img.service;


import actiOn.Img.storeImg.StoreImg;
import actiOn.Img.storeImg.StoreImgRepository;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImgService {
    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_NAME;

    @Value("${cloud.aws.region.static}")
    private String REGION;

    private final StoreImgRepository storeImgRepository;

    public ImgService(StoreImgRepository storeImgRepository) {
        this.storeImgRepository = storeImgRepository;
    }

    // 프로필 이미지 등록
    public String uploadProfileImage(MultipartFile file, Member member) throws IOException {
        // S3에 이미지 파일 업로드
        String imageName = generateRandomName(file, Math.toIntExact(member.getMemberId()));
        String fileUrl = uploadImage(file, imageName);

        return fileUrl;
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

        List<StoreImg> storeImgs = new ArrayList<>();

        int index = 1;
        for (MultipartFile file : files) {
            if (file == null) {
                continue;
            }

            String fileName = generateRandomName(file, index);
            String fileUrl = uploadImage(file, fileName);
            StoreImg storeImg = new StoreImg(fileUrl, store);

            if (file.equals(thumbnailImage)) {
                storeImg = StoreThumbnailImgIdGenerator(store, storeImg);
            }
            storeImgs.add(storeImg);
            index++;
        }
        storeImgRepository.saveAll(storeImgs);
    }

    private String uploadImage(MultipartFile file, String imageName) throws IOException {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION)
                .build();

        // 메타데이터 추가
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getInputStream().available());

        s3Client.putObject(new PutObjectRequest(
                BUCKET_NAME, imageName, file.getInputStream(), metadata)
        );

        return s3Client.getUrl(BUCKET_NAME, imageName).toString();
    }

    private String generateRandomName(MultipartFile file, int index) {
        String uuid = UUID.randomUUID().toString();

        return uuid + index + "-" + file.getOriginalFilename();
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
