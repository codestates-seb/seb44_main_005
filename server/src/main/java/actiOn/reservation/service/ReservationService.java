package actiOn.reservation.service;

import actiOn.auth.utils.AuthUtil;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.item.entity.Item;
import actiOn.item.repository.ItemRepository;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.entity.ReservationItem;
import actiOn.reservation.repository.ReservationItemRepository;
import actiOn.reservation.repository.ReservationRepository;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final MemberService memberService;
    private final ItemRepository itemRepository;
    private final ReservationItemRepository reservationItemRepository;

    public ReservationService(ReservationRepository reservationRepository, StoreRepository storeRepository, MemberService memberService, ItemRepository itemRepository, ReservationItemRepository reservationItemRepository) {
        this.reservationRepository = reservationRepository;
        this.storeRepository = storeRepository;
        this.memberService = memberService;
        this.itemRepository = itemRepository;
        this.reservationItemRepository = reservationItemRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void postReservation(Long storeId, Reservation reqReservation) {
        //Todo store 존재하는지 여부 확인 -> 예외처리 리팩토링 필요
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));
        reqReservation.setStore(store);

        //예약 날짜 유효성 검사 -> 오늘 기준 이전 날짜가 오면 에러 발생
        validateReservationDate(reqReservation.getReservationDate());

        //로그인한 유저의 정보를 reservation에 담기
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member member = memberService.findMemberByEmail(loginUserEmail);
        reqReservation.setMember(member);

       //reqReservation에 있는 상품 id가 존재하는지 확인 후 reservationItem 저장
        List<ReservationItem> saveReservationItems = createReservationItem(reqReservation);
        reqReservation.setReservationItems(saveReservationItems);

        //예약 정보 저장
        reservationRepository.save(reqReservation);

        //Todo 예약 성공 시 ticket 개수 줄여야함.
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateReservation(Long reservationId, Reservation updateReservation){
        //수정할 예약 정보 조회 찾기
        Reservation findReservation = this.findByReservation(reservationId);

        //로그인한 회원 정보와 reservation의 member와 동일한지 검증
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member member = memberService.findMemberByEmail(loginUserEmail);
        memberService.loginMemberEqualReservaionMember(member.getMemberId(),findReservation.getMember().getMemberId());

        Optional.ofNullable(updateReservation.getReservationName())
                .ifPresent(reservationName -> {
                    findReservation.setReservationName(reservationName);
                });
        Optional.ofNullable(updateReservation.getReservationPhone())
                .ifPresent(reservationPhone -> {
                    findReservation.setReservationPhone(reservationPhone);
                });
        Optional.ofNullable(updateReservation.getReservationEmail())
                .ifPresent(reservationEmail -> {
                    findReservation.setReservationEmail(reservationEmail);
                });

        reservationRepository.save(findReservation);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelReservation(Long reservationId){
        Reservation findReservation = this.findByReservation(reservationId);

        //로그인한 회원 정보와 reservation의 member와 동일한지 검증
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member member = memberService.findMemberByEmail(loginUserEmail);
        memberService.loginMemberEqualReservaionMember(member.getMemberId(),findReservation.getMember().getMemberId());

        //예약 대기 -> 예약 취소
        findReservation.setReservationStatus(Reservation.ReservationStatus.RESERVATION_CANCLE);
        reservationRepository.delete(findReservation);

        //Todo 예약 취소 -> 환불
    }

    @Transactional(readOnly = true)
    public Reservation getReservations(Long reservationId){
        Reservation reservation = this.findByReservation(reservationId);
        return reservation;
    }

    //예약 찾기
    private Reservation findByReservation(Long reservationId){
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.RESERVATION_NOT_FOUND));
    }

    // 예약 날짜 검증 메서드
    private void validateReservationDate(LocalDate reservationDate) {
        if (reservationDate != null) {
            LocalDate today = LocalDate.now();
            if (reservationDate.isBefore(today)) {
                throw new IllegalArgumentException("예약 날짜는 이전 날짜에 적용할 수 없습니다.");
            }
        }
    }

    //예약 상품 생성 및 저장 메서드
    private List<ReservationItem> createReservationItem(Reservation reservation) {
        List<ReservationItem> saveReservationItemList = new ArrayList<>();
        List<ReservationItem> reservationItemList = reservation.getReservationItems();

        for (ReservationItem reservationItem : reservationItemList) {
            Long itemId = reservationItem.getItem().getItemId();
            //itemId로 해당 상품을 찾았음
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
            //Todo 티켓 valid 검사 -> 잔여수 컬럼이 필요할 듯
            item.validateTicketCount(reservationItem.getTicketCount());

            //ReservationItem 저장
            ReservationItem saveReservationItem = new ReservationItem();
            saveReservationItem.setTicketCount(reservationItem.getTicketCount());
            saveReservationItem.setItem(item);
            saveReservationItem.setReservation(reservation);

            saveReservationItemList.add(saveReservationItem);
            //예약상품 저장
            reservationItemRepository.save(saveReservationItem);
        }
        //총 금액과 sumTicketCount의 가격이 동일한지 검증
        validateTotalPrice(saveReservationItemList, reservation.getTotalPrice());

        return saveReservationItemList;
    }

    //예약 금액 검증 메서드
    private void validateTotalPrice(List<ReservationItem> reservationItems, int totalPrice){
        int sumTicketCount = 0;
        for(ReservationItem reservationItem : reservationItems){
            sumTicketCount += reservationItem.getTicketCount() * reservationItem.getItem().getPrice();
        }
        if (totalPrice != sumTicketCount){
            throw new IllegalArgumentException("예약하신 총 금액과 각 티켓 값의 총 금액이 일치하지 않습니다.");
        }
    }
}