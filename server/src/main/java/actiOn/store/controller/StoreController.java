package actiOn.store.controller;

import actiOn.Img.service.ImgService;
import actiOn.map.response.GeoLocation;
import actiOn.map.service.KakaoMapService;
import actiOn.store.dto.StorePostDto;
import actiOn.store.entity.Store;
import actiOn.store.mapper.StoreMapper;
import actiOn.store.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping
@Validated
public class StoreController {
    private final StoreService storeService;
    private final StoreMapper mapper;
    private final ImgService imgService;

    public StoreController(StoreService storeService,StoreMapper mapper, ImgService imgService) {
        this.storeService = storeService;
        this.mapper = mapper;
        this.imgService = imgService;
    }

    // 업체 등록
    @PostMapping("/stores")
    public ResponseEntity postStore(@RequestBody @Valid StorePostDto storePostDto) {
        Store store = mapper.storePostDtoToStore(storePostDto);
        List<MultipartFile> images = storePostDto.getStoreImage();
        imgService.uploadStoreImage(images,store);
        Store storeSaveResult = storeService.createStore(store);
        //Todo
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 업체 수정
    @PatchMapping("/stores/{store-id}")
    public ResponseEntity patchStore(@PathVariable("store-id") @Positive long storeId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 업체 상세 페이지 조회, 업체 수정 페이지 렌더링용
    @GetMapping("/stores/{store-id}")
    public ResponseEntity getStore(@PathVariable("store-id") @Positive long storeId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 업체 삭제
    @DeleteMapping("/stores/{store-id}")
    public ResponseEntity deleteStore(@PathVariable("store-id") @Positive long storeId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 메인 페이지
    @GetMapping("/main")
    public ResponseEntity getMainPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 전체 카테고리 페이지 조회
    @GetMapping("/stores")
    public ResponseEntity getCategoryPage(@RequestParam(name = "category") String category,
                                          @RequestParam(name = "sort_field") String sortField) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 검색 기능
    @GetMapping("/search")
    public ResponseEntity getSearchResults(@RequestParam(name = "keyword") String keyword) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
