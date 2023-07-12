package actiOn.review.service;

import actiOn.auth.utils.AuthUtil;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.reservation.entity.Reservation;
import actiOn.review.dto.ReviewResponseDto;
import actiOn.review.dto.ReviewsResponseDto;
import actiOn.review.entity.Review;
import actiOn.review.mapper.ReviewMapper;
import actiOn.review.repository.ReviewRepository;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final ReviewMapper reviewMapper;
    private final MemberService memberService;

    public ReviewService(ReviewRepository reviewRepository, StoreRepository storeRepository, ReviewMapper reviewMapper, MemberService memberService) {
        this.reviewRepository = reviewRepository;
        this.storeRepository = storeRepository;
        this.reviewMapper = reviewMapper;
        this.memberService = memberService;
    }

    public Review createReview(Long storeId, Review review) {
        //Todo 로그인한 회원의 정보 가져오기
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member findMember = memberService.findMemberByEmail(loginUserEmail);

        //Todo 업체 존재 여부 확인
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));

        //Todo 로그인한 회원의 정보가 해당 업체를 예약했는지의 여부 확인 -> 리팩토링 필수
        List<Reservation> reservationList = store.getReservations();
        boolean hasReservationEmail = reservationList.stream()
                .anyMatch(reservation -> reservation.getReservationId() == (findMember.getMemberId()));
        if (hasReservationEmail) {
            review.setMember(findMember);
            review.setStore(store);
        } else {
            throw new IllegalArgumentException("예약한 회원만 리뷰를 작성할 수 있습니다.");
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
                .reviewResponseDtoList(reviewResponseDtos)
                .build();

        return reviewsResponseDtos;
    }
}
