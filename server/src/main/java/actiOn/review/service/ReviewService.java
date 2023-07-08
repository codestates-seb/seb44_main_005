package actiOn.review.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.reservation.entity.Reservation;
import actiOn.review.dto.ReviewResponseDto;
import actiOn.review.dto.ReviewsResponseDto;
import actiOn.review.entity.Review;
import actiOn.review.mapper.ReviewMapper;
import actiOn.review.repository.ReviewRepository;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, StoreRepository storeRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.storeRepository = storeRepository;
        this.reviewMapper = reviewMapper;
    }

    public Review createReview(Long storeId,
                               Review review,
                               Authentication authentication) {
        //Todo 로그인한 회원의 정보 가져오기
        String loginUserEmail = authentication.getName();
//        Member loginMember = memberRepository.findByEmail(loginUserEmail).orElseThrow(
//                () -> new BusinessLogicException(ExceptionCode.MEMBER_EXISTS));

        //Todo 업체 존재 여부 확인 -> 리팩토링 필요
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));

        //Todo 로그인한 회원의 정보가 해당 업체를 예약했는지의 여부 확인
        List<Reservation> reservationList = store.getReservations();
        boolean hasReservationEmail = reservationList.stream()
                .anyMatch(reservation -> reservation.getReservationEmail().equals(loginUserEmail));
        if (hasReservationEmail) {
//            review.setMember(loginMember);
            review.setStore(store);
        } else {
            throw new IllegalArgumentException("예약한 회원만 리뷰를 작성할 수 있습니다.");
        }
        return review;
    }

    public ReviewsResponseDto getAllReviews(Long storeId) {
        //Todo 업체 존재 여부 확인 -> 리팩토링 필요
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));

        //Todo Store 기준 모든 리뷰 조회
        List<Review> reviews = reviewRepository.findAllByStore(store);

        //Todo 해당 store를 조회한 리뷰의 총 개수
        int reviewCount = reviews.size();

        //Todo 해당 store의 평균값 구하기
        double ratingAvg = reviews.stream()
                .mapToInt(review -> review.getRating())
                .average()
                .orElse(0.0);
        ratingAvg = Math.round(ratingAvg * 10) / 10.0; // 소수점 한자리까지 반올림


        //Todo 조회한 리뷰를 리뷰 DTO로 매핑
        List<ReviewResponseDto> reviewResponseDtos = reviewMapper.reviewsToReviewsResponseDto(reviews);

        ReviewsResponseDto reviewsResponseDtos = ReviewsResponseDto.builder()
                .reviewCount(reviewCount)
                .ratingAvg(ratingAvg)
                .reviewResponseDtoList(reviewResponseDtos)
                .build();

        return reviewsResponseDtos;
    }

}
