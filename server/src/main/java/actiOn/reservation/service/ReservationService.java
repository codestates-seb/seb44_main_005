package actiOn.reservation.service;

import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.item.entity.Item;
import actiOn.item.repository.ItemRepository;
import actiOn.member.entity.Member;
import actiOn.member.repository.MemberRepository;
import actiOn.reservation.entity.Reservation;
import actiOn.reservation.entity.ReservationItem;
import actiOn.reservation.repository.ReservationItemRepository;
import actiOn.reservation.repository.ReservationRepository;
import actiOn.store.entity.Store;
import actiOn.store.repository.StoreRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ReservationItemRepository reservationItemRepository;

    public ReservationService(ReservationRepository reservationRepository, StoreRepository storeRepository, MemberRepository memberRepository, ItemRepository itemRepository, ReservationItemRepository reservationItemRepository) {
        this.reservationRepository = reservationRepository;
        this.storeRepository = storeRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
        this.reservationItemRepository = reservationItemRepository;
    }

    public Reservation postReservation(Long storeId, Reservation reqReservation, Authentication authentication){
        //Todo store 존재하는지 여부 확인 -> 예외처리 리팩토링 필요
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.STORE_NOT_FOUND));

        //Todo 예약 날짜 유효성 검사 -> 오늘 기준 이전 날짜가 오면 에러 발생
        if (reqReservation.getReservationDate() != null){
            Date today = new Date();
            if (reqReservation.getReservationDate().before(today)){
                //Todo 예외처리 리팩토링 필요
                throw new IllegalArgumentException("예약 날짜는 이전 날짜에 적용할 수 없습니다.");
            }
        }

       //Todo 로그인한 유저의 정보를 reservation에 담아야함
//        String loginUserEmail = authentication.getName();
//        Member findMember = memberRepository.findByEmail(loginUserEmail).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));


        // Todo reqReservation에 있는 상품 id가 존재하는지 확인 후 reservationItem 저장
        List<ReservationItem> saveReservationItemList = new ArrayList<>();
        List<ReservationItem> reservationItemList = reqReservation.getReservationItems();
        for (ReservationItem reservationItem : reservationItemList){
            // reqReservation에 있는 itemId
            Long itemId = reservationItem.getItem().getItemId();
            // Todo itemId로 해당 상품을 찾았음 -> 예외처리 리팩토링 필요
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

            //Todo 티켓 수가 maxCount보다 크면 에러처리 발생


            ReservationItem saveReservationItem = new ReservationItem();
            saveReservationItem.setTicketCount(reservationItem.getTicketCount());
            saveReservationItem.setItem(item);
            saveReservationItem.setReservation(reqReservation);

            saveReservationItemList.add(saveReservationItem);

            //예약상품 저장
            reservationItemRepository.save(saveReservationItem);
        }
        reqReservation.setReservationItems(saveReservationItemList);

        //예약 정보 저장
        return reservationRepository.save(reqReservation);
    }

//    public Reservation getReservations(Long storeId, Authentication authentication){
//
//    }
}
