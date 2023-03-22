package actiOn.reservation.service;

import actiOn.auth.utils.AuthUtil;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.item.dto.ItemDto;
import actiOn.item.entity.Item;
import actiOn.item.service.ItemService;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.entity.ReservationItem;
import actiOn.reservation.repository.ReservationItemRepository;
import actiOn.reservation.repository.ReservationRepository;
import actiOn.store.entity.Store;
import actiOn.store.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Transactional
@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StoreService storeService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final ReservationItemRepository reservationItemRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void postReservation(Long storeId, Reservation reservation, List<ReservationItem> reservationItems) {
        Store store = storeService.findStoreByStoreId(storeId);
        reservation.setStore(store);

        //예약 날짜 유효성 검사
        validateReservationDate(reservation.getReservationDate());

        //로그인한 유저의 정보를 reservation에 담기
        String email = AuthUtil.getCurrentMemberEmail();
        Member member = memberService.findMemberByEmail(email);
        reservation.setMember(member);

        // reservationItem 저장
        List<ReservationItem> newReservationItems = generateReservationItem(reservation, reservationItems);
        reservation.setReservationItems(newReservationItems);

        // 입력된 총 금액과 총 예약 가격이 동일한지 검증
        validateTotalPrice(newReservationItems, reservation.getTotalPrice());

        //예약 정보 저장
        reservationRepository.save(reservation);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateReservation(Long reservationId, Reservation updateReservation) {
        //수정할 예약 정보 조회 찾기
        Reservation findReservation = findReservation(reservationId);

        //로그인한 회원 정보와 reservation의 member와 동일한지 검증
        verifyReservationMember(findReservation.getMember());

        Optional.ofNullable(updateReservation.getReservationName())
                .ifPresent(findReservation::setReservationName);
        Optional.ofNullable(updateReservation.getReservationPhone())
                .ifPresent(findReservation::setReservationPhone);
        Optional.ofNullable(updateReservation.getReservationEmail())
                .ifPresent(findReservation::setReservationEmail);

        reservationRepository.save(findReservation);
    }

    // 예약 검증 및 조회
    @Transactional(readOnly = true)
    public Reservation getReservations(Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        verifyReservationMember(reservation.getMember());

        return reservation;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelReservation(Long reservationId) {
        Reservation findReservation = findReservation(reservationId);

        //로그인한 회원 정보와 reservation의 member와 동일한지 검증
        verifyReservationMember(findReservation.getMember());

        //예약 대기 -> 예약 취소
        findReservation.setReservationStatus(Reservation.ReservationStatus.RESERVATION_CANCEL);
        reservationRepository.delete(findReservation);

        //Todo 예약 취소 -> 환불
    }

    //예약 상품 생성 및 저장 메서드
    private List<ReservationItem> generateReservationItem(Reservation reservation, List<ReservationItem> reservationItems) {
        List<ReservationItem> newReservationItems = new ArrayList<>();

        for (ReservationItem reservationItem : reservationItems) {
            reservationItem.setReservation(reservation);
            newReservationItems.add(reservationItem);
        }

        return reservationItemRepository.saveAll(newReservationItems);
    }

    public List<ItemDto> findItemsByStoreIdAndDate(long storeId, LocalDate date) {
        try {
            //Todo 예외처리하기
            List<ItemDto> itemDtos = new ArrayList<>();
            Store findStore = storeService.findStoreByStoreId(storeId);
            List<Item> findItems = findStore.getItems();
            Map<Long, Integer> reservationTickets = reservationTicketCount(findStore, date);
            for (Item item : findItems) {
                int reservationTicketCount = // 예약된 티켓이 없다면 null로 나오므로, null과 int는 연산이 불가능하므로, int로 변환
                        reservationTickets.containsKey(item.getItemId())
                                ? reservationTickets.get(item.getItemId()) : 0;

                int remainingTicketCount = item.getTotalTicket() - reservationTicketCount;
                if (remainingTicketCount < 0) remainingTicketCount = 0;
                ItemDto itemDto = new ItemDto(
                        item.getItemId(),
                        item.getItemName(),
                        item.getTotalTicket(),
                        item.getPrice(),
                        remainingTicketCount
                );
                itemDtos.add(itemDto);
            }
            return itemDtos;
        } catch (Exception e) {
            return null;
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

    // 로그인한 회원과 reservation member 일치하는지 확인
    private Member verifyReservationMember(Member reservationMember) {
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member findMember = memberService.findMemberByEmail(loginUserEmail);

        if (!findMember.equals(reservationMember)) {
            throw new BusinessLogicException(ExceptionCode.RESERVATION_MEMBER_NOT_FOUND);
        }

        return findMember;
    }

    //예약 찾기
    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.RESERVATION_NOT_FOUND));
    }

    // 예약 날짜 검증
    private void validateReservationDate(LocalDate reservationDate) {
        // 오늘 기준 이전 날짜가 오면 에러 발생
        LocalDate today = LocalDate.now();

        if (reservationDate.isBefore(today)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_RESERVATION_DATE);
        }
    }

    // 예약 금액 검증 메서드
    private void validateTotalPrice(List<ReservationItem> reservationItems, int totalPrice) {
        int sumTotalPrice = 0;
        for (ReservationItem reservationItem : reservationItems) {
            int reservationItemPrice = reservationItem.getItem().getPrice() * reservationItem.getTicketCount();
            sumTotalPrice += reservationItemPrice;
        }

        if (totalPrice != sumTotalPrice) {
            throw new BusinessLogicException(ExceptionCode.RESERVATION_TOTAL_PRICE_MISMATCH);
        }
    }
}