package actiOn.business.service;

import actiOn.business.entity.Business;
import actiOn.business.repository.BusinessRepository;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class BusinessService {
    private final BusinessRepository businessRepository;
    private final MemberService memberService;

    public BusinessService(BusinessRepository businessRepository, MemberService memberService) {
        this.businessRepository = businessRepository;
        this.memberService = memberService;
    }

    // 비즈니스 등록
    public Business createBusiness(Business business, String email) {
        // member 연결
        Member member = memberService.findMemberByEmail(email);
        business.setMember(member);

        return businessRepository.save(business);
    }

    // 사업자 등록번호 검증
    public void verifyRegistrationNumber(String registrationNumber) {
        // 형식 검증
        verifyRegistrationNumberFormat(registrationNumber);

        // 중복 검증
        Optional<Business> business =
                businessRepository.findBusinessByRegistrationNumber(registrationNumber);

        if (business.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.BUSINESS_EXISTS);
        }
    }

    // 사업자 등록번호 입력 형식 검증
    private void verifyRegistrationNumberFormat(String registrationNumber) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{5}");

        if (!pattern.matcher(registrationNumber).matches()) {
            throw new BusinessLogicException(ExceptionCode.INVALID_REGISTRATION_NUMBER);
        }
    }
}
