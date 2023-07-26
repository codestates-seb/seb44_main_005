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

    // 리뷰 생성
    @Transactional
    public Review createReview(Long storeId, Review review) {
        // store 검증
        String email = AuthUtil.getCurrentMemberEmail();
        Member findMember = memberService.findMemberByEmail(email);
        Store store = storeService.findStoreByStoreId(storeId);

        // 회원이 해당 업체를 예약한 내역이 있는지 확인
        reservationService.verifyReservationByMemberAndStore(findMember, store);

        // 회원이 해당 업체를 이용완료 했는지 확인
        reservationService.verifyReservationByReservationStatus(findMember, store);

        // 이용 완료한 예약 횟수와 사용자가 이미 작성한 리뷰 개수를 비교
        verifyAlreadyWroteReviews(findMember, store);

        //review 내용 욕설 검증
        verifyBadWord(review);

        review.setMember(findMember);
        review.setStore(store);

        return reviewRepository.save(review);
    }

    // 업체에 리뷰 개수 등록
    @Transactional
    public void updateStoreReviewCounting(Long storeId, Review review) {
        Store store = storeService.findStoreByStoreId(storeId);

        // 해당 업체의 리뷰 개수
        int totalReviewCount = store.getReviewCount();

        // 해당 업체의 총점
        double nowTotalRating = totalReviewCount * store.getRating();

        // 더해진 업체의 총점
        double addedTotalRating = nowTotalRating + review.getRating();

        // 업데이트되어야하는 업체의 평점
        double avgRating = addedTotalRating / (totalReviewCount + 1);

        // 전체 평점의 평균 저장 및 리뷰 개수 추가
        storeService.updateRatingAndReviewCount(store, avgRating);
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

    private void verifyBadWord(Review review) {
        BadWordFiltering badWordFiltering = new BadWordFiltering();

        if (badWordFiltering.blankCheck(review.getContent())) {
            throw new BusinessLogicException(ExceptionCode.BAD_WORD_NOT_ALLOWED);
        }
    }

    // 사용자가 이용완료한 예약 횟수와 사용자가 이미 작성한 리뷰 개수를 비교
    private void verifyAlreadyWroteReviews(Member findMember, Store store) {
        // 회원이 해당 업체에 예약한 횟수
        int reservationCount = reservationService.countReservation(store, findMember);
        // 회원이 해당 업체에 작성한 리뷰의 개수
        int myReviewCount = reviewRepository.countByStoreAndMember(store, findMember);

        // 이미 작성했으면 에러 던짐
        if (reservationCount <= myReviewCount) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_WROTE_A_REVIEW);
        }
    }
}
