package actiOn.store.controller;

import actiOn.Img.service.ImgService;
import actiOn.auth.utils.AuthUtil;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.store.dto.*;
import actiOn.store.dto.mainrep.MainPageResponseDto;
import actiOn.store.entity.Store;
import actiOn.store.mapper.CategoryResponseMapper;
import actiOn.store.mapper.StoreMapper;
import actiOn.store.mapper.StoreResponseMapper;
import actiOn.store.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@Validated
@AllArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final StoreMapper storeMapper;
    private final StoreResponseMapper responseMapper;
    private final CategoryResponseMapper categoryResponseMapper;
    private final ImgService imgService;
    private final MemberService memberService;

    // 업체 등록
    @PostMapping("/stores") // 스토어 생성
    public ResponseEntity postStore(@RequestBody @Valid StorePostDto storePostDto) {
        Store store = storeMapper.storePostDtoToStore(storePostDto); // dto를 store로 변환
        store.setMember(memberService.findMemberByEmail(AuthUtil.getCurrentMemberEmail())); //store에 맴버 주입
        Store savedStore = storeService.createStore(store); // 스토어 생성
        StoreIdResponseDto storeIdResponseDto = storeMapper.storeToStorePostResponseDto(savedStore);
        return new ResponseEntity<>(storeIdResponseDto,HttpStatus.CREATED);
    }

    @PostMapping("/storeImages/{store-id}") // 스토어 이미지 업로드
    public ResponseEntity storeImgUpload(@PathVariable("store-id") long storeId,
                                            @RequestPart("images") List<MultipartFile> images,
                                         @RequestParam("thumbnailImage") MultipartFile thumbnailImage) {
        String memberEmail = AuthUtil.getCurrentMemberEmail();
        if (memberEmail.equals("anonymousUser")){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (imgService.uploadStoreImage(images,storeId,thumbnailImage) != null){
            return new ResponseEntity<>(HttpStatus.CREATED);}
        //Todo store에 member 주입
        return new ResponseEntity<>("이미지 업로드 실패",HttpStatus.BAD_REQUEST);
    }

    // 업체 수정
    @PatchMapping("/stores/{store-id}") // 스토어 글 수정
    public ResponseEntity patchStore(@PathVariable("store-id") @Positive long storeId,
                                     @RequestBody StorePatchDto storePatchDto) {
        Store store = storeMapper.storePatchDtoToStore(storePatchDto); // dto를 store로 변환
        storeService.verifyIdentityToStore(storeId, AuthUtil.getCurrentMemberEmail()); // 본인 자격 검증
        Store patchStore = storeService.updateStore(store,storeId); // 스토어 생성
        StoreIdResponseDto storeIdResponseDto = storeMapper.storeToStorePostResponseDto(patchStore);
        return new ResponseEntity<>(storeIdResponseDto,HttpStatus.OK);//
    }

    @PatchMapping("/storeImages/{store-id}") // 스토어 이미지 업로드
    public ResponseEntity storeImgPatch(@PathVariable("store-id") long storeId,
                                         @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                         @RequestParam(value = "thumbnailImage", required = false) MultipartFile thumbnailImage,
                                        @RequestParam(value = "deleteImages", required = false) List<String> deleteImages) {
        String memberEmail = AuthUtil.getCurrentMemberEmail();
        if (memberEmail.equals("anonymousUser")) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (images == null) images = new ArrayList<>();
        if (imgService.uploadStoreImage(images,storeId,thumbnailImage) != null){
            storeService.deleteStoreImgByLinks(deleteImages);
            return new ResponseEntity<>(HttpStatus.OK);}
    //Todo store에 member 주입
        return new ResponseEntity<>("이미지 업로드 실패",HttpStatus.BAD_REQUEST);
    }
    // 업체 상세 페이지 조회, 업체 수정 페이지 렌더링용
    @GetMapping("/stores/{store-id}") // 스토어 상세페이지, 수정페이지 랜더링을 위해 필요한 리소스 응답
    public ResponseEntity getStore(@PathVariable("store-id") @Positive long storeId) {
        Store findStore = storeService.findStoreByStoreId(storeId);
        StoreResponseDto responseDto = responseMapper.storeToStoreResponseDto(findStore);
        //Todo member 인증정보를 가져와서 isLike 속성 반영해줘야 함.
        String memberEmail = AuthUtil.getCurrentMemberEmail();
        if (!memberEmail.equals("anonymousUser")){
            Member member = memberService.findMemberByEmail(memberEmail);
            StoreResponseDto storeResponseDto = storeService.insertWishAtStoreResponseDto(member, responseDto, findStore.getStoreId());
            return new ResponseEntity<>(storeResponseDto,HttpStatus.OK);
        }
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    // 업체 삭제 PP_003
    @DeleteMapping("/stores/{store-id}") //스토어 삭제
    public ResponseEntity deleteStore(@PathVariable("store-id") @Positive Long storeId) {
        storeService.deleteStore(storeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 메인 페이지
    @GetMapping("/main") // 메인페이지
    public ResponseEntity getMainPage() {
        MainPageResponseDto mainPageResponseDto = storeService.getMainPage();
        return new ResponseEntity<>(mainPageResponseDto,HttpStatus.OK);
    }

    // 전체 카테고리 페이지 조회
    @GetMapping("/stores") // 카테고리 페이지
    public ResponseEntity getCategoryPage(@RequestParam(name = "category") String category,
                                          @RequestParam(name = "sort_field") String sortField) {
        //Todo 로직 추가해야함
        List<Store> findStoreByCategory = storeService.findStoreByCategory(category,sortField);
        CategoryResponseDto categoryResponseDto =
                categoryResponseMapper.CategoryStoreToCategoryResponseDto(findStoreByCategory);
        if (categoryResponseDto == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //Todo member 인증정보를 가져와서 isLike 속성 반영해줘야 함.
        String memberEmail = AuthUtil.getCurrentMemberEmail();
        if (!memberEmail.equals("anonymousUser")){
            Member member = memberService.findMemberByEmail(memberEmail);
            CategoryResponseDto categoryResponseDtoWithLike =
                    storeService.insertWishAtCategoryResponseDto(member, categoryResponseDto);
            return new ResponseEntity<>(categoryResponseDtoWithLike,HttpStatus.OK);
        }
        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }

    // 검색 기능
    @GetMapping("/search") //검색
    public ResponseEntity getSearchResults(@RequestParam(name = "keyword") String keyword) {
        //Todo 공백 검색불가하게 하자
        List<Store> searchResult = storeService.searchEnginOnStoreNameByKeyword(keyword);
        CategoryResponseDto searchResultTransformDto =
                categoryResponseMapper.CategoryStoreToCategoryResponseDto(searchResult); // response form이 같아서 재활용
        String memberEmail = AuthUtil.getCurrentMemberEmail();
        if (!memberEmail.equals("anonymousUser")){
            Member member = memberService.findMemberByEmail(memberEmail);
            CategoryResponseDto searchResponseDtoWithLike =
                    storeService.insertWishAtCategoryResponseDto(member, searchResultTransformDto);
            return new ResponseEntity<>(searchResponseDtoWithLike,HttpStatus.OK);
        }
        return new ResponseEntity<>(searchResultTransformDto,HttpStatus.OK);
    }
}
