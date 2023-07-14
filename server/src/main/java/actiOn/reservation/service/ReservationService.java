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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final MemberService memberService;
    private final ItemRepository itemRepository;
    private final ReservationItemRepository reservationItemRepository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void postReservation(Long storeId, Reservation reqReservation) {
        //예약날짜 잘못들어올시 예외처리
        checkDateTime(reqReservation.getReservationDate());

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
        List<ReservationItem> saveReservationItems = createReservationItem(reqReservation, store);
        reqReservation.setReservationItems(saveReservationItems);

        ///todo remaining ticket 초과시 error 발생
//        checkRemainingTicketCount(saveReservationItems); //V1

        //예약 정보 저장
        reservationRepository.save(reqReservation);


    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateReservation(Long reservationId, Reservation updateReservation) {
        //수정할 예약 정보 조회 찾기
        Reservation findReservation = this.findByReservation(reservationId);

        //로그인한 회원 정보와 reservation의 member와 동일한지 검증
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member member = memberService.findMemberByEmail(loginUserEmail);
        memberService.loginMemberEqualReservationMember(member.getMemberId(), findReservation.getMember().getMemberId());

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

        findReservation.setModifiedAt(LocalDateTime.now());
        reservationRepository.save(findReservation);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelReservation(Long reservationId) {
        Reservation findReservation = this.findByReservation(reservationId);

        //로그인한 회원 정보와 reservation의 member와 동일한지 검증
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member member = memberService.findMemberByEmail(loginUserEmail);
        memberService.loginMemberEqualReservationMember(member.getMemberId(), findReservation.getMember().getMemberId());

        //예약 대기 -> 예약 취소
        findReservation.setReservationStatus(Reservation.ReservationStatus.RESERVATION_CANCLE);
        reservationRepository.delete(findReservation);

        //Todo 예약 취소 -> 환불
    }

    @Transactional(readOnly = true)
    public Reservation getReservations(Long reservationId) {
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        memberService.findMemberByEmail(loginUserEmail);

        Reservation reservation = this.findByReservation(reservationId);

        return reservation;
    }

    //예약 찾기
    private Reservation findByReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.RESERVATION_NOT_FOUND));
    }

    // 예약 날짜 검증 메서드
    private void validateReservationDate(LocalDate reservationDate) {
        if (reservationDate != null) {
            LocalDate today = LocalDate.now();
            if (reservationDate.isBefore(today)) {
                throw new BusinessLogicException(ExceptionCode.RESERVATION_DATE_INVALID);
            }
        }
    }

    //예약 상품 생성 및 저장 메서드
    private List<ReservationItem> createReservationItem(Reservation reservation, Store store) {
        List<ReservationItem> saveReservationItemList = new ArrayList<>();
        List<ReservationItem> reservationItemList = reservation.getReservationItems();

        //예약날짜
        LocalDate reservedDate = reservation.getReservationDate();
        for (ReservationItem reservationItem : reservationItemList) {
            //예약한 업체의 아이템 ID
            Long itemId = reservationItem.getItem().getItemId();

            //store의 item이 예약한 상품의 item과 맞는지 검증
            validateReservedItem(store,reservationItem.getItem());

            //itemId로 해당 상품을 찾았음
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));
            //티켓 valid 검사
            item.validateTicketCount(reservationItem.getTicketCount());

            //todo check Remaining 티켓 -> 오류
//            checkRemainingTicketCount(store,item,reservedDate,reservationItem);


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
    private void validateTotalPrice(List<ReservationItem> reservationItems, int totalPrice) {
        int sumTicketCount = 0;
        for (ReservationItem reservationItem : reservationItems) {
            sumTicketCount += reservationItem.getTicketCount() * reservationItem.getItem().getPrice();
        }
        if (totalPrice != sumTicketCount) {
            throw new BusinessLogicException(ExceptionCode.TOTAL_PRICE_INVALID);
        }
    }

    public Map<Long, Integer> reservationTicketCount(Store store, LocalDate currentDate) throws BusinessLogicException {
        //Todo 예약내역에서 1. 날짜,예약상태로 필터링 한 예약 정보들 // 그러면 조건에 맞는 예약들이 나옴 //
        //선택한 업체와 날짜의 예약들을 가져옴
        List<Reservation> currentDateReservations =
                reservationRepository.findByReservationDateAndStore(currentDate, store);
        Map<Long, Integer> remainingTicketInfo = new HashMap<>();
        for (Reservation reservation : currentDateReservations) {
            List<ReservationItem> reservationItems = reservation.getReservationItems();
            for (ReservationItem reservationItem : reservationItems) {
                long itemId = reservationItem.getItem().getItemId();
                int ticketCount = reservationItem.getTicketCount();// 해당 아이템의 예약한 티켓 수
                // 주석 부분 수정
                if (remainingTicketInfo.containsKey(itemId)) {
                    int existingTicketCount = remainingTicketInfo.get(itemId);
                    remainingTicketInfo.put(itemId, existingTicketCount + ticketCount);
                } else {
                    remainingTicketInfo.put(itemId, ticketCount);
                }
            }
        }
        return remainingTicketInfo;
    }

    //잘못된 형식의 날짜가 들어온 경우 예외
    private void checkDateTime(LocalDate reservationDate){
        String checkDate = String.valueOf(reservationDate);
        if (!checkDate.matches("\\d{4}-\\d{2}-\\d{2}")){
            throw new BusinessLogicException(ExceptionCode.CHECK_DATE);
        }
    }

    //todo remaining 티켓 검증 메서드 -> 오류
    private void checkRemainingTicketCount(Store store, Item reservedItem, LocalDate reservedDate, ReservationItem reservedReservationItem){
        //현재 예약한 티켓 개수 -> 반복문을 이미 받고 있어서 reservedTicket 값은 바뀜
        int reservedTicket = reservedReservationItem.getTicketCount();

        //업체에서 예약한 날짜에 이전까지 아이템을 예약한 티켓값의 총 값을 가져오기
            //업체의 예약한 날짜 예약 리스트 가져오기
            List<Reservation> reservations = reservationRepository.findByReservationDateAndStore(reservedDate,store);
            //예약 리스트를 반복문을 통해 예약 한개의 상품들을 보기
            for (Reservation reservation : reservations){
                //예약 정보의 예약 상품들 가져오기
                List<ReservationItem> reservationItems = reservation.getReservationItems();
                int itemTotalTicket = 0;
                //한 개의 예약 상품 보기
                for (ReservationItem reservationItem : reservationItems){
                    //한 개의 예약 상품에서 예약한 상품(reservedItem)과 같은 아이템들의 이전까지 예약한 TICKET 개수 모두 가져오기
                    if (reservationItem.getItem().getItemId().equals(reservedItem.getItemId())) {
                        int ticketCount = reservationItemRepository.getTotalTicketCountByItem(reservedItem);
                        itemTotalTicket += ticketCount;
                        break;
                    }
                }
                //itemTotalTicket 값을 사용해서 예약한 티켓 개수 검증
                if (reservedTicket + itemTotalTicket > reservedItem.getMaxCount()){
                    throw new BusinessLogicException(ExceptionCode.RESERVATION_TICKET_EXCEEDED);
                }
            }
    }

    //store의 item이 예약한 상품의 item과 맞는지 검증
    private void validateReservedItem(Store store, Item reservedItem){
        List<Item> storeItems = store.getItems();
        boolean itemFound = false;
        for (Item item : storeItems){
            if (item.getItemId().equals(reservedItem.getItemId())){
                itemFound = true;
                break;
            }
        }
        if (!itemFound){
            throw new BusinessLogicException(ExceptionCode.STORE_ITEM_INVALID);
        }
    }

}