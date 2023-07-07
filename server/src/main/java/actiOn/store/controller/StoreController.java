package actiOn.store.controller;

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

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping
@Validated
public class StoreController {
    private final StoreService storeService;
    private final StoreMapper mapper;

    public StoreController(StoreService storeService,StoreMapper mapper) {
        this.storeService = storeService;
        this.mapper = mapper;
    }

    // 업체 등록
    @PostMapping("/stores")
    public ResponseEntity postStore(@RequestBody @Valid StorePostDto storePostDto) {
        Store store = mapper.storePostDtoToStore(storePostDto);
        Store storeSaveResult = storeService.createStore(store);

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
