package actiOn.member.controller;

import actiOn.Img.profileImg.ProfileImgDto;
import actiOn.auth.utils.AuthUtil;
import actiOn.business.dto.BusinessDto;
import actiOn.business.entity.Business;
import actiOn.business.mapper.BusinessMapper;
import actiOn.business.service.BusinessService;
import actiOn.member.dto.*;
import actiOn.member.entity.Member;
import actiOn.member.mapper.MemberMapper;
import actiOn.member.service.MemberService;
import actiOn.wish.dto.WishlistResponseDto;
import actiOn.wish.mapper.WishMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@Validated
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final BusinessService businessService;
    private final BusinessMapper businessMapper;
    private final WishMapper wishMapper;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity signupMember(@RequestBody @Valid MemberPostDto.Signup requestBody) {
        Member member = memberMapper.memberPostSignupDtoToMember(requestBody);
        memberService.createMember(member);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO 회원 프로필 사진 등록
    @PutMapping("/mypage/profile")
    public ResponseEntity uploadProfileImage(Authentication authentication,
                                             @RequestBody @Valid ProfileImgDto requestBody) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO 회원 프로필 사진 삭제

    // 마이페이지 - 회원 정보 수정
    @PatchMapping("/mypage")
    public ResponseEntity updateMember(@RequestBody @Valid MemberPatchDto requestBody) {

        Member member = memberMapper.memberPatchDtoToMember(requestBody);
        member.setEmail(AuthUtil.getCurrentMemberEmail());

        memberService.updateMember(member);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 마이페이지 - 회원 정보 조회
    @GetMapping("/mypage")
    public ResponseEntity getMemberInfo() {
        String email = AuthUtil.getCurrentMemberEmail();
        Member member = memberService.findMemberByEmail(email);

        MemberResponseDto response = memberMapper.memberToMemberResponseDto(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 파트너 등록
    @PostMapping("/partners")
    public ResponseEntity registerPartner(@RequestBody @Valid BusinessDto.Post requestBody) {
        String email = AuthUtil.getCurrentMemberEmail();
        Business business = businessMapper.BusinessPostDtoToBusiness(requestBody);

        businessService.createBusiness(business, email);
        memberService.registerPartnership(business, email);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 사업자 등록번호 검증
    @GetMapping("/partners/verify")
    public ResponseEntity checkBusinessNumber(@RequestParam(name = "number") String registrationNumber) {
        String email = AuthUtil.getCurrentMemberEmail();
        memberService.findMemberByEmail(email);

        businessService.verifyRegistrationNumber(registrationNumber);

        return new ResponseEntity<>(registrationNumber, HttpStatus.OK);
    }

    // 마이페이지 - 사업자 정보 조회
    @GetMapping("/mypage/partner")
    public ResponseEntity getPartnerInfo() {
        String email = AuthUtil.getCurrentMemberEmail();
        Member partner = memberService.findMemberByEmail(email);

        PartnerResponseDto response = memberMapper.partnerToPartnerResponseDto(partner);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 위시리스트 조회
    @GetMapping("/mypage/wishlist")
    public ResponseEntity getMemberWishlist() {
        String email = AuthUtil.getCurrentMemberEmail();
        Member member = memberService.findMemberByEmail(email);

        WishlistResponseDto response = wishMapper.memberToWishlistResponseDto(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // TODO 마이페이지 - 예약 정보 조회
    @GetMapping("/mypage/reservations")
    public ResponseEntity getMemberReservations(Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 마이페이지 - 판매 서비스 관리
    @GetMapping("/mystores")
    public ResponseEntity getPartnerStores() {
        String email = AuthUtil.getCurrentMemberEmail();
        Member partner = memberService.findMemberByEmail(email);

        PartnerStoreResponseDto response = memberMapper.partnerToPartnerStoreResponseDto(partner);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
