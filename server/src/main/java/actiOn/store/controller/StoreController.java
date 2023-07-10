package actiOn.store.controller;

import actiOn.Img.service.ImgService;
import actiOn.map.response.GeoLocation;
import actiOn.map.service.KakaoMapService;
import actiOn.member.entity.Member;
import actiOn.store.dto.StorePostDto;
import actiOn.store.dto.StoreResponseDto;
import actiOn.store.entity.Store;
import actiOn.store.mapper.StoreMapper;
import actiOn.store.mapper.StoreResponseMapper;
import actiOn.store.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@Validated
public class StoreController {
    private final StoreService storeService;
    private final StoreMapper mapper;
    private final StoreResponseMapper responseMapper;
    private final ImgService imgService;

    public StoreController(StoreService storeService,StoreMapper mapper, ImgService imgService, StoreResponseMapper storeResponseMapper) {
        this.storeService = storeService;
        this.mapper = mapper;
        this.imgService = imgService;
        this.responseMapper = storeResponseMapper;
    }

    // 업체 등록
    @PostMapping("/stores")
    public ResponseEntity postStore(@RequestBody @Valid StorePostDto storePostDto) {
        Store store = mapper.storePostDtoToStore(storePostDto);
        Store storeSaveResult = storeService.createStore(store);
//        StoreResponseDto result = mapper.storeToStoreResponseDto(storeSaveResult);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/storeImages/{store-id}")
    public ResponseEntity storeImgUpload(@PathVariable("store-id") long storeId,
                                            @RequestPart("images") List<MultipartFile> images) {
            imgService.uploadStoreImage(images,storeId);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/profileImages")
    public ResponseEntity profileImgUpload(@RequestPart("image") MultipartFile image){
        imgService.uploadProfileImage(image,new Member());
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
        Store findStore = storeService.findStoreByStoreId(storeId);
        StoreResponseDto responseDto = responseMapper.storeToStoreResponseDto(findStore);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
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
