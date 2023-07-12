package actiOn.auth.utils;

import actiOn.Img.storeImg.StoreImg;
import actiOn.Img.storeImg.StoreImgRepository;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StubDataLoader implements CommandLineRunner {

    private final StoreRepository storeRepository;
    private final StoreImgRepository storeImgRepository;

    public StubDataLoader(StoreRepository storeRepository, StoreImgRepository storeImgRepository) {
        this.storeRepository = storeRepository;
        this.storeImgRepository = storeImgRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //이미지
        List<StoreImg> storeImgList1 = new ArrayList<>();
        StoreImg storeImg1 = new StoreImg();
        storeImg1.setImgId(1L);
        storeImg1.setLink("https://www.job-post.co.kr/news/photo/202106/25358_23433_2551.jpg");
        storeImg1.setIsThumbnail(true);
        storeImgRepository.save(storeImg1);

        StoreImg storeImg2 = new StoreImg();
        storeImg2.setImgId(2L);
        storeImg2.setLink("https://www.job-post.co.kr/news/photo/202106/25358_23433_2551.jpg");
        storeImg2.setIsThumbnail(false);
        storeImgRepository.save(storeImg2);

        storeImgList1.add(storeImg1);
        storeImgList1.add(storeImg2);

        List<StoreImg> storeImgList2 = new ArrayList<>();
        StoreImg storeImg3 = new StoreImg();
        storeImg3.setImgId(3L);
        storeImg3.setLink("https://www.job-post.co.kr/news/photo/202106/25358_23433_2551.jpg");
        storeImg3.setIsThumbnail(true);
        storeImgRepository.save(storeImg3);

        StoreImg storeImg4 = new StoreImg();
        storeImg4.setImgId(4L);
        storeImg4.setLink("https://www.job-post.co.kr/news/photo/202106/25358_23433_2551.jpg");
        storeImg4.setIsThumbnail(false);
        storeImgRepository.save(storeImg4);

        storeImgList2.add(storeImg3);
        storeImgList2.add(storeImg4);

        List<StoreImg> storeImgList3 = new ArrayList<>();
        StoreImg storeImg5 = new StoreImg();
        storeImg5.setImgId(5L);
        storeImg5.setLink("https://www.job-post.co.kr/news/photo/202106/25358_23433_2551.jpg");
        storeImg5.setIsThumbnail(true);
        storeImgRepository.save(storeImg5);

        StoreImg storeImg6 = new StoreImg();
        storeImg6.setImgId(6L);
        storeImg6.setLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQq1vCXhZqkqMlvMFGFxnSDrscf61Eap4-hcQ&usqp=CAU");
        storeImg6.setIsThumbnail(false);
        storeImgRepository.save(storeImg6);

        storeImgList3.add(storeImg5);
        storeImgList3.add(storeImg6);

        List<StoreImg> storeImgList4 = new ArrayList<>();
        StoreImg storeImg7 = new StoreImg();
        storeImg7.setImgId(7L);
        storeImg7.setLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKqa0LnyJtfOOgfZeZdgcwm5sQDGi371NTpw&usqp=CAU");
        storeImg7.setIsThumbnail(true);
        storeImgRepository.save(storeImg7);

        StoreImg storeImg8 = new StoreImg();
        storeImg8.setImgId(8L);
        storeImg8.setLink("https://www.job-post.co.kr/news/photo/202106/25358_23433_2551.jpg");
        storeImg8.setIsThumbnail(false);
        storeImgRepository.save(storeImg8);

        storeImgList4.add(storeImg7);
        storeImgList4.add(storeImg8);

        Store bestStore1 = new Store();
        bestStore1.setStoreId(1L);
        bestStore1.setStoreName("언더워터플레이 함덕점");
        bestStore1.setCategory("수상스키");
        bestStore1.setBody("제주도 시원한 바닷속에서 다양한 물고기들을...");
        bestStore1.setAddress("제주도 제주읍 제주시");
        bestStore1.setLatitude(25.111111111);
        bestStore1.setLongitude(24.111111111);
        bestStore1.setKakao("kakaoHello");
        bestStore1.setContact("010-1111-1111");
        bestStore1.setLikeCount(100);
        bestStore1.setReviewCount(100);
        bestStore1.setRating(4.9);
        bestStore1.setLowPrice(10000);
        bestStore1.setStoreImgList(storeImgList1);
        storeRepository.save(bestStore1);

        Store bestStore2 = new Store();
        bestStore2.setStoreId(2L);
        bestStore2.setStoreName("수상레저 다있어점");
        bestStore2.setCategory("수상레저");
        bestStore2.setBody("수상레저로 한여름의 더위 날려...");
        bestStore2.setAddress("제주도 제주읍 제주시");
        bestStore2.setLatitude(25.1111116111);
        bestStore2.setLongitude(24.1111311111);
        bestStore2.setKakao("kakaoTalk");
        bestStore2.setContact("010-1112-1112");
        bestStore2.setLikeCount(99);
        bestStore2.setReviewCount(100);
        bestStore2.setRating(4.6);
        bestStore2.setLowPrice(90000);
        bestStore2.setStoreImgList(storeImgList2);
        storeRepository.save(bestStore2);

        Store bestStore3 = new Store();
        bestStore3.setStoreId(3L);
        bestStore3.setStoreName("월정퀵서프");
        bestStore3.setCategory("서핑");
        bestStore3.setBody("에메랄드 빛 해변에서 이루어지는 전문 서핑 강습...");
        bestStore3.setAddress("제주도 제주읍 함덕시");
        bestStore3.setLatitude(25.1111111112);
        bestStore3.setLongitude(24.1111111311);
        bestStore3.setKakao("kakaoSearch");
        bestStore3.setContact("010-1321-1211");
        bestStore3.setLikeCount(98);
        bestStore3.setReviewCount(94);
        bestStore3.setRating(3.6);
        bestStore3.setLowPrice(1000900);
        bestStore3.setStoreImgList(storeImgList3);
        storeRepository.save(bestStore3);

        Store bestStore4 = new Store();
        bestStore4.setStoreId(4L);
        bestStore4.setStoreName("제주도 ATV체험");
        bestStore4.setCategory("ATV");
        bestStore4.setBody("제주도에서 많이 즐기는 액티비티의 꽃 ATV...");
        bestStore4.setAddress("제주도 제주읍 제주시");
        bestStore4.setLatitude(25.1611111111);
        bestStore4.setLongitude(24.2111111111);
        bestStore4.setKakao("kakaoMap");
        bestStore4.setContact("010-1321-1211");
        bestStore4.setLikeCount(93);
        bestStore4.setReviewCount(91);
        bestStore4.setRating(3.2);
        bestStore4.setLowPrice(104300);
        bestStore4.setStoreImgList(storeImgList4);
        storeRepository.save(bestStore4);
    }
}
