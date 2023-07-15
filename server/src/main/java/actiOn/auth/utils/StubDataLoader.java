package actiOn.auth.utils;

import actiOn.Img.storeImg.StoreImg;
import actiOn.Img.storeImg.StoreImgRepository;
import actiOn.item.entity.Item;
import actiOn.item.repository.ItemRepository;
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
    private final ItemRepository itemRepository;

    public StubDataLoader(StoreRepository storeRepository, StoreImgRepository storeImgRepository, ItemRepository itemRepository) {
        this.storeRepository = storeRepository;
        this.storeImgRepository = storeImgRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Store bestStore1 = new Store();
        bestStore1.setStoreId(1L);
        bestStore1.setStoreName("언더워터플레이 함덕점");
        bestStore1.setCategory("스노클링/다이빙");
        bestStore1.setBody("제주도 시원한 바닷속에서 다양한 물고기들을...");
        bestStore1.setAddress("제주도 제주읍 제주시");
        bestStore1.setLatitude(33.509565870437);
        bestStore1.setLongitude(126.479180622209);
        bestStore1.setKakao("kakaoHello");
        bestStore1.setContact("010-1111-1111");
        bestStore1.setLikeCount(100);
        bestStore1.setReviewCount(100);
        bestStore1.setRating(4.9);
        bestStore1.setLowPrice(10000);
        storeRepository.save(bestStore1);

        //-----------------------------------------------------------//

        Store bestStore2 = new Store();
        bestStore2.setStoreId(2L);
        bestStore2.setStoreName("수상레저 다있어점");
        bestStore2.setCategory("수상레저");
        bestStore2.setBody("수상레저로 한여름의 더위 날려...");
        bestStore2.setAddress("제주도 제주읍 제주시");
        bestStore2.setLatitude(33.509665870437);
        bestStore2.setLongitude(126.479180622209);
        bestStore2.setKakao("kakaoTalk");
        bestStore2.setContact("010-1112-1112");
        bestStore2.setLikeCount(99);
        bestStore2.setReviewCount(100);
        bestStore2.setRating(4.6);
        bestStore2.setLowPrice(90000);
        storeRepository.save(bestStore2);

        //------------------------------------------//

        Store bestStore3 = new Store();
        bestStore3.setStoreId(3L);
        bestStore3.setStoreName("월정퀵서프");
        bestStore3.setCategory("서핑");
        bestStore3.setBody("에메랄드 빛 해변에서 이루어지는 전문 서핑 강습...");
        bestStore3.setAddress("제주도 제주읍 함덕시");
        bestStore3.setLatitude(33.509535870437);
        bestStore3.setLongitude(126.479180622209);
        bestStore3.setKakao("kakaoSearch");
        bestStore3.setContact("010-1321-1211");
        bestStore3.setLikeCount(98);
        bestStore3.setReviewCount(94);
        bestStore3.setRating(3.6);
        bestStore3.setLowPrice(1000900);
        storeRepository.save(bestStore3);

        Store bestStore4 = new Store();
        bestStore4.setStoreId(4L);
        bestStore4.setStoreName("제주도 ATV체험");
        bestStore4.setCategory("ATV");
        bestStore4.setBody("제주도에서 많이 즐기는 액티비티의 꽃 ATV...");
        bestStore4.setAddress("제주도 제주읍 제주시");
        bestStore4.setLatitude(33.509525870437);
        bestStore4.setLongitude(126.479180622209);
        bestStore4.setKakao("kakaoMap");
        bestStore4.setContact("010-1321-1211");
        bestStore4.setLikeCount(0);
        bestStore4.setReviewCount(91);
        bestStore4.setRating(3.2);
        bestStore4.setLowPrice(104300);
        storeRepository.save(bestStore4);


        //이미지 set
        List<StoreImg> storeImgList1 = new ArrayList<>();
        StoreImg storeImg1 = new StoreImg();
        storeImg1.setImgId(1L);
        storeImg1.setLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRV0IlkaymZEgDAQfVmTTMIrm4nRqawHOwMWA&usqp=CAU");
        storeImg1.setIsThumbnail(true);
        storeImg1.setStore(bestStore1);

        StoreImg storeImg2 = new StoreImg();
        storeImg2.setImgId(2L);
        storeImg2.setLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvTWeJ4zfReFrDCHaMnjZBGEmM-pLFNgd7oQ&usqp=CAU");
        storeImg2.setIsThumbnail(false);
        storeImg2.setStore(bestStore1);

        storeImgRepository.save(storeImg1);
        storeImgRepository.save(storeImg2);

        storeImgList1.add(storeImg1);
        storeImgList1.add(storeImg2);
        bestStore1.setStoreImgList(storeImgList1);
        storeRepository.save(bestStore1);

        //--------------------------------------------------------------//

        List<StoreImg> storeImgList2 = new ArrayList<>();
        StoreImg storeImg3 = new StoreImg();
        storeImg3.setImgId(3L);
        storeImg3.setLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ2MU1T_daMAj_TCOxxS_WUMmLnYy57FBM8mw&usqp=CAU");
        storeImg3.setIsThumbnail(true);
        storeImg3.setStore(bestStore2);

        StoreImg storeImg4 = new StoreImg();
        storeImg4.setImgId(4L);
        storeImg4.setLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRi4Bo1grekwpSHrA7-IB0PZBIiCdTQTZlJWA&usqp=CAU");
        storeImg4.setIsThumbnail(false);
        storeImg4.setStore(bestStore2);

        storeImgRepository.save(storeImg3);
        storeImgRepository.save(storeImg4);

        storeImgList2.add(storeImg3);
        storeImgList2.add(storeImg4);
        bestStore2.setStoreImgList(storeImgList2);
        storeRepository.save(bestStore2);

        //------------------------------------------------------------//

        List<StoreImg> storeImgList3 = new ArrayList<>();
        StoreImg storeImg5 = new StoreImg();
        storeImg5.setImgId(5L);
        storeImg5.setLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZYGC_IVAshC3is3qyNX8EmXvm5hX2f2T-UA&usqp=CAU");
        storeImg5.setIsThumbnail(true);
        storeImg5.setStore(bestStore3);

        StoreImg storeImg6 = new StoreImg();
        storeImg6.setImgId(6L);
        storeImg6.setLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhNpcNEK2AnEQfGDDydlRg5tw-7bvd4XewNQ&usqp=CAU");
        storeImg6.setIsThumbnail(false);
        storeImg6.setStore(bestStore3);

        storeImgRepository.save(storeImg5);
        storeImgRepository.save(storeImg6);

        storeImgList3.add(storeImg5);
        storeImgList3.add(storeImg6);
        bestStore3.setStoreImgList(storeImgList3);
        storeRepository.save(bestStore3);

        //--------------------------------------------------------//

        List<StoreImg> storeImgList4 = new ArrayList<>();
        StoreImg storeImg7 = new StoreImg();
        storeImg7.setImgId(7L);
        storeImg7.setLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTddjByXIAlIRGnr12e5EhH54T6_yUYvmrh-w&usqp=CAU");
        storeImg7.setIsThumbnail(true);
        storeImg7.setStore(bestStore4);

        StoreImg storeImg8 = new StoreImg();
        storeImg8.setImgId(8L);
        storeImg8.setLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSG3Q1-tfq7cV873Y4fvoppMrSMlUo1vG4Gwg&usqp=CAU");
        storeImg8.setIsThumbnail(false);
        storeImg8.setStore(bestStore4);

        storeImgRepository.save(storeImg7);
        storeImgRepository.save(storeImg8);

        storeImgList4.add(storeImg7);
        storeImgList4.add(storeImg8);
        bestStore3.setStoreImgList(storeImgList4);
        storeRepository.save(bestStore3);

        //Item을 만들자
        List<Item> items1 = new ArrayList<>();

        Item item1 = new Item();
        item1.setItemId(1L);
        item1.setItemName("제주 서귀포 잠수함");
        item1.setTotalTicket(10);
        item1.setPrice(36270);
        item1.setStore(bestStore1);

        Item item2 = new Item();
        item2.setItemId(2L);
        item2.setItemName("가디언다이브 체험 스쿠버 다이빙");
        item2.setTotalTicket(10);
        item2.setPrice(95000);
        item2.setStore(bestStore1);

        itemRepository.save(item1);
        itemRepository.save(item2);

        items1.add(item1);
        items1.add(item2);

        bestStore1.setItems(items1);
        storeRepository.save(bestStore1);
        //----------------------------------//

        List<Item> items2 = new ArrayList<>();

        Item item3 = new Item();
        item3.setItemId(3L);
        item3.setItemName("돌고래 에코투어 선셋(요트투어)");
        item3.setTotalTicket(10);
        item3.setPrice(39060);
        item3.setStore(bestStore2);

        Item item4 = new Item();
        item4.setItemId(4L);
        item4.setItemName("제주도 서귀포 그랑블루요트 선셋");
        item4.setTotalTicket(10);
        item4.setPrice(26040);
        item4.setStore(bestStore2);

        itemRepository.save(item3);
        itemRepository.save(item4);

        items1.add(item3);
        items1.add(item4);

        bestStore2.setItems(items2);
        storeRepository.save(bestStore2);
        //----------------------------------//

        List<Item> items3 = new ArrayList<>();

        Item item5 = new Item();
        item5.setItemId(5L);
        item5.setItemName("중문색달해변 서핑 강습");
        item5.setTotalTicket(10);
        item5.setPrice(60000);
        item5.setStore(bestStore3);

        Item item6 = new Item();
        item6.setItemId(6L);
        item6.setItemName("누디다이브 범섬 체험 다이빙");
        item6.setTotalTicket(10);
        item6.setPrice(79000);
        item6.setStore(bestStore3);

        itemRepository.save(item5);
        itemRepository.save(item6);

        items1.add(item1);
        items1.add(item2);

        bestStore3.setItems(items3);
        storeRepository.save(bestStore3);
        //----------------------------------//

        List<Item> items4 = new ArrayList<>();

        Item item7 = new Item();
        item7.setItemId(7L);
        item7.setItemName("율랜드 스쿠버 다이빙 체험");
        item7.setTotalTicket(10);
        item7.setPrice(100000);
        item7.setStore(bestStore4);

        Item item8 = new Item();
        item8.setItemId(8L);
        item8.setItemName("제주 투명카약/바다카약/스노클링 체험");
        item8.setTotalTicket(10);
        item8.setPrice(10000);
        item8.setStore(bestStore4);

        itemRepository.save(item7);
        itemRepository.save(item8);

        items1.add(item7);
        items1.add(item8);

        bestStore1.setItems(items4);
        storeRepository.save(bestStore4);
        //----------------------------------//
    }
}

