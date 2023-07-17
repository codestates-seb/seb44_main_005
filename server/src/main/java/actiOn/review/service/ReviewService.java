package actiOn.review.service;

import actiOn.auth.utils.AuthUtil;
import actiOn.auth.utils.badword.BadWordFiltering;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.service.ReservationService;
import actiOn.review.dto.ReviewResponseDto;
import actiOn.review.dto.ReviewsResponseDto;
import actiOn.review.entity.Review;
import actiOn.review.mapper.ReviewMapper;
import actiOn.review.repository.ReviewRepository;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final ReviewMapper reviewMapper;
    private final MemberService memberService;
    private final ReservationService reservationService;

//    public ReviewService(ReviewRepository reviewRepository, StoreRepository storeRepository, ReviewMapper reviewMapper, MemberService memberService) {
//        this.reviewRepository = reviewRepository;
//        this.storeRepository = storeRepository;
//        this.reviewMapper = reviewMapper;
//        this.memberService = memberService;
//    }

    public Review createReview(Long storeId, Review review) {
        //review 내용 욕설 검증
        BadWordFiltering badWordFiltering = new BadWordFiltering();
        if (badWordFiltering.blankCheck(review.getContent())){
            throw new IllegalArgumentException("리뷰에 욕설이 포함되어 있습니다.");
        }

        //Todo 로그인한 회원의 정보 가져오기
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member findMember = memberService.findMemberByEmail(loginUserEmail);

        //Todo 업체 존재 여부 확인
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));

        //Todo 로그인한 회원의 정보가 해당 업체를 예약했는지의 여부 확인 -> 리팩토링 필수
//        List<Reservation> reservationList = store.getReservations();
//        boolean hasReservationEmail = reservationList.stream()
//                .anyMatch(reservation -> reservation.getReservationId() == (findMember.getMemberId()));
        //해당 예약에 리뷰 이력이 없을 것 + reservation에서 스토어와 멤버에 해당하는게 있는지 확인
        //Todo countReservation()를 이용해서 예약숫자 조회 // 그리고 조건에 맞는 리뷰카운트 해서 여유있으면 리뷰 남기게 해주기
        int reservationCount = reservationService.countReservation(store,findMember);
        int nowReviewCount = reviewRepository.countByStoreAndMember(store,findMember);
        if (reservationCount > nowReviewCount) {
            review.setMember(findMember);
            review.setStore(store);
        } else {
            throw new BusinessLogicException(ExceptionCode.REVIEW_CREATE_REJECTED);
        }
        Review saveReview = reviewRepository.save(review);

        //Todo review 전체 평점의 평균
        Double avgRating = reviewRepository.avgStoreRating(store);
        store.setRating(avgRating);
        storeRepository.save(store);

        //Todo store에 있는 review 개수 추가
        storeRepository.addReviewCount(store);

        return saveReview;
    }

    public ReviewsResponseDto getAllReviews(Long storeId) {
        //Todo 업체 존재 여부 확인 -> 리팩토링 필요
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));

        //Todo Store 기준 모든 리뷰 조회
        List<Review> reviews = reviewRepository.findAllByStore(store);

        //Todo 조회한 리뷰를 리뷰 DTO로 매핑
        List<ReviewResponseDto> reviewResponseDtos = reviewMapper.reviewsToReviewsResponseDto(reviews);

        ReviewsResponseDto reviewsResponseDtos = ReviewsResponseDto.builder()
                .reviewCount(store.getReviewCount())
                .ratingAvg(store.getRating())
                .reviews(reviewResponseDtos)
                .build();

        return reviewsResponseDtos;
    }
}
