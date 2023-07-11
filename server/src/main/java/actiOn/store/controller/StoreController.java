package actiOn.store.controller;

import actiOn.Img.service.ImgService;
import actiOn.item.dto.ItemDto;
import actiOn.item.entity.Item;
import actiOn.member.entity.Member;
import actiOn.store.dto.CategoryResponseDto;
import actiOn.store.dto.StorePostDto;
import actiOn.store.dto.StorePostResponseDto;
import actiOn.store.dto.StoreResponseDto;
import actiOn.store.entity.Store;
import actiOn.store.mapper.CategoryResponseMapper;
import actiOn.store.mapper.StoreMapper;
import actiOn.store.mapper.StoreResponseMapper;
import actiOn.store.service.StoreService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping
@Validated
public class StoreController {
    private final StoreService storeService;
    private final StoreMapper mapper;
    private final StoreResponseMapper responseMapper;
    private final CategoryResponseMapper categoryResponseMapper;
    private final ImgService imgService;

    public StoreController(StoreService storeService,StoreMapper mapper,
                           ImgService imgService, StoreResponseMapper storeResponseMapper,
                           CategoryResponseMapper categoryResponseMapper) {
        this.storeService = storeService;
        this.mapper = mapper;
        this.imgService = imgService;
        this.responseMapper = storeResponseMapper;
        this.categoryResponseMapper = categoryResponseMapper;
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
            if (imgService.uploadStoreImage(images,storeId) != null){
                return new ResponseEntity<>(HttpStatus.CREATED);}
            return new ResponseEntity<>("이미지 업로드 실패",HttpStatus.BAD_REQUEST);
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

    @GetMapping("/stores/{store-id}")
    public ResponseEntity getStoreByDate(@PathVariable("store-id") @Positive long storeId,
                                         @RequestParam("date") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date){

        List<ItemDto> items = storeService.findItemsByStoreIdAndDate(storeId,date);
        if (items != null) {
            return new ResponseEntity<>(items, HttpStatus.OK);
        }
        return new ResponseEntity<>("예약정보를 불러오는데 실패했습니다.", HttpStatus.NOT_FOUND);
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
        List<Store> findStoreByCategory = storeService.findStoreByCategory(category,sortField);
        CategoryResponseDto categoryResponseDto =
                categoryResponseMapper.CategoryStoreToCategoryResponseDto(findStoreByCategory);

        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }

    // 검색 기능
    @GetMapping("/search") //검색
    public ResponseEntity getSearchResults(@RequestParam(name = "keyword") String keyword) {
        List<Store> searchResult = storeService.searchEnginOnStoreNameByKeyword(keyword);
        CategoryResponseDto searchResultTransformDto =
                categoryResponseMapper.CategoryStoreToCategoryResponseDto(searchResult); // response form이 같아서 재활용
        return new ResponseEntity<>(searchResultTransformDto,HttpStatus.OK);
    }
}
