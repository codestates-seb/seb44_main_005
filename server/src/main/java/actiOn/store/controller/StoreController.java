package actiOn.store.controller;

import actiOn.Img.service.ImgService;
import actiOn.map.response.GeoLocation;
import actiOn.map.service.KakaoMapService;
import actiOn.member.entity.Member;
import actiOn.store.dto.StorePostDto;
import actiOn.store.dto.StorePostResponseDto;
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
    @PostMapping("/stores") // 스토어 생성
    public ResponseEntity postStore(@RequestBody @Valid StorePostDto storePostDto) {
        Store store = mapper.storePostDtoToStore(storePostDto);
        Store storeSaveResult = storeService.createStore(store);
        //Todo 등록하는 사장님 정보를 받아서 Member 객체 매핑
        StorePostResponseDto storePostResponseDto = mapper.storeToStorePostResponseDto(storeSaveResult);
        return new ResponseEntity<>(storePostResponseDto,HttpStatus.CREATED);
    }
    @PostMapping("/storeImages/{store-id}") // 스토어 이미지 업로드
    public ResponseEntity storeImgUpload(@PathVariable("store-id") long storeId,
                                            @RequestPart("images") List<MultipartFile> images) {
            imgService.uploadStoreImage(images,storeId);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/profileImages") // 프로필 이미지 업로드
    public ResponseEntity profileImgUpload(@RequestPart("image") MultipartFile image){
        imgService.uploadProfileImage(image,new Member());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    // 업체 수정
    @PatchMapping("/stores/{store-id}") // 스토어 글 수정
    public ResponseEntity patchStore(@PathVariable("store-id") @Positive long storeId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 업체 상세 페이지 조회, 업체 수정 페이지 렌더링용
    @GetMapping("/stores/{store-id}") // 스토어 상세페이지, 수정페이지 랜더링을 위해 필요한 리소스 응답
    public ResponseEntity getStore(@PathVariable("store-id") @Positive long storeId) {
        Store findStore = storeService.findStoreByStoreId(storeId);
        StoreResponseDto responseDto = responseMapper.storeToStoreResponseDto(findStore);
        //Todo member 인증정보를 가져와서 isLike 속성 반영해줘야 함.
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    // 업체 삭제
    @DeleteMapping("/stores/{store-id}") //스토어 삭제
    public ResponseEntity deleteStore(@PathVariable("store-id") @Positive long storeId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 메인 페이지
    @GetMapping("/main") // 메인페이지
    public ResponseEntity getMainPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 전체 카테고리 페이지 조회
    @GetMapping("/stores") // 카테고리 페이지
    public ResponseEntity getCategoryPage(@RequestParam(name = "category") String category,
                                          @RequestParam(name = "sort_field") String sortField) {
        //Todo 로직 추가해야함
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 검색 기능
    @GetMapping("/search") //검색
    public ResponseEntity getSearchResults(@RequestParam(name = "keyword") String keyword) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
