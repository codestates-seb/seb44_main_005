package actiOn.review.service;

import actiOn.auth.utils.AuthUtil;
import actiOn.auth.utils.badword.BadWordFiltering;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.reservation.service.ReservationService;
import actiOn.review.entity.Review;
import actiOn.review.repository.ReviewRepository;
import actiOn.store.entity.Store;
import actiOn.store.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final StoreService storeService;
    private final MemberService memberService;
    private final ReservationService reservationService;

    public Review createReview(Long storeId, Review review) {
        //review 내용 욕설 검증
        BadWordFiltering badWordFiltering = new BadWordFiltering();
        if (badWordFiltering.blankCheck(review.getContent())) {
            throw new BusinessLogicException(ExceptionCode.BAD_WORD_NOT_ALLOWED);
        }

        // store 검증
        String email = AuthUtil.getCurrentMemberEmail();
        Member findMember = memberService.findMemberByEmail(email);
        Store store = storeService.findStoreByStoreId(storeId);

        //Todo 로그인한 회원의 정보가 해당 업체를 예약했는지의 여부 확인 -> 리팩토링 필수
//        List<Reservation> reservationList = store.getReservations();
//        boolean hasReservationEmail = reservationList.stream()
//                .anyMatch(reservation -> reservation.getReservationId() == (findMember.getMemberId()));
        //해당 예약에 리뷰 이력이 없을 것 + reservation에서 스토어와 멤버에 해당하는게 있는지 확인
        //Todo countReservation()를 이용해서 예약숫자 조회 // 그리고 조건에 맞는 리뷰카운트 해서 여유있으면 리뷰 남기게 해주기
        int reservationCount = reservationService.countReservation(store, findMember);
        long nowReviewCount = reviewRepository.countByStoreAndMember(store,findMember);

        if (reservationCount > nowReviewCount) {
            review.setMember(findMember);
            review.setStore(store);
        } else {
            throw new BusinessLogicException(ExceptionCode.ALREADY_WROTE_A_REVIEW);
        }

        Review saveReview = reviewRepository.save(review);

        // 지금 해당 업체의 총점
        double nowTotalRating = nowReviewCount * store.getRating();
        // 더해진 업체의 총점
        double addedTotalRating = nowTotalRating + review.getRating();

        // 업데이트되어야하는 업체의 평점
        double avgRating = addedTotalRating / (nowReviewCount + 1);

        // 전체 평점의 평균 저장 및 리뷰 개수 추가
        storeService.updateRatingAndReviewCount(store, avgRating);

        return saveReview;
    }

    // 업체의 모든 리뷰 탐색
    public List<Review> getAllReviews(Long storeId) {
        // 업체 존재 여부 확인 -> 리팩토링 필요
        Store store = storeService.findStoreByStoreId(storeId);

        // Store 기준 모든 리뷰 조회
        List<Review> reviews = reviewRepository.findAllByStoreOrderByCreatedAtDesc(store);

        return reviews;
    }

    public double getStoreAvgRating(long storeId) {
        Store store = storeService.findStoreByStoreId(storeId);

        return store.getRating();
    }
}
