package actiOn.reservation.service;

import actiOn.auth.utils.AuthUtil;
import actiOn.exception.BusinessLogicException;
import actiOn.exception.ExceptionCode;
import actiOn.item.dto.ItemDto;
import actiOn.item.entity.Item;
import actiOn.item.service.ItemService;
import actiOn.member.entity.Member;
import actiOn.member.service.MemberService;
import actiOn.payment.dto.PaymentInfoDto;
import actiOn.payment.entity.Payment;
import actiOn.payment.mapper.PaymentMapper;
import actiOn.payment.service.PaymentService;
import actiOn.redis.service.RedisService;
import actiOn.reservation.dto.ReservationItemDto;
import actiOn.reservation.dto.ReservationPostDto;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import com.google.gson.Gson;
@Transactional
@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StoreService storeService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final ReservationItemRepository reservationItemRepository;
    private final PaymentService paymentService;
    private final RedisService redisService;
    private final PaymentMapper paymentMapper;



/**    여기서부터는 예약 및 결제를 save 하기 위한 영역입니다.     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String redisSaveReservation(Long storeId, ReservationPostDto reservationPostDto) {
        validateIdentity(storeId);// 신원검증
        List<ReservationItemDto> reservationItems = new ArrayList<>();
        for (ReservationItemDto reservationItemDto : reservationPostDto.getReservationItems()){
            if (reservationItemDto.getTicketCount()==0) continue;
            reservationItems.add(reservationItemDto);
        }
        reservationPostDto.setReservationItems(reservationItems);

        reservationPostDto.setStoreId(storeId);
        String redisKey = UUID.randomUUID().toString().replace("-", "")
                +String.valueOf(System.currentTimeMillis()); // random문자열 + timeStamp로 키값 생성
        Gson gson = new Gson();
        String json = gson.toJson(reservationPostDto);
        redisService.saveDataToRedis(redisKey,json); //redis에 json으로 자동 파싱되어 저장 //불러올때도 엔티티로 자동 파싱
        return redisKey;
    }
    private void validateIdentity(Long storeId){
        memberService.findMemberByEmail(AuthUtil.getCurrentMemberEmail());
        storeService.findStoreByStoreId(storeId);
    }

    public ReservationPostDto getReservationPostDtoFromRedis(String reservationKey){
        String jsonFromRedis = redisService.getDataFromRedis(reservationKey);
        Gson gson = new Gson();
        ReservationPostDto reservationPostDto = gson.fromJson(jsonFromRedis, ReservationPostDto.class);
        redisService.deleteDataFromRedis(reservationKey); // 조회하면 삭제(1회용)
        return reservationPostDto;
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void postReservation(long storeId, Reservation reservationFromRedis,List<ReservationItem> reservationItems,Payment payment) {

        try {
            confirmReservationWithPayment(reservationFromRedis, payment);
            Reservation entity = createReservationEntity(storeId, reservationFromRedis, reservationItems);
            entity.setPaymentKey(payment.getPaymentKey());
            entity.setReservationStatus(Reservation.ReservationStatus.RESERVATION_CONFIRM);
            reservationRepository.save(entity);
        } catch (BusinessLogicException ex) {
            handleRollback(payment);
        }
    }
    @Transactional
    public void handleRollback(Payment payment) {
        paymentService.refund(payment.getPaymentKey(),payment.getTotalAmount());
        throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
    }
    private void confirmReservationWithPayment(Reservation reservation, Payment payment){

        if (!payment.getStatus().equals(Payment.Status.DONE)) throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
        else if (reservation.getTotalPrice()!=payment.getTotalAmount()) {
                paymentService.refund(payment.getPaymentKey(),payment.getTotalAmount());
                throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
        }
    }
    public Payment createPaymentByOrderId(String orderId){
        PaymentInfoDto paymentInfoDto = paymentService.getPaymentInfoByOrderId(orderId);
        Payment payment = paymentMapper.paymentInfoDtoToPayment(paymentInfoDto);
        return paymentService.saveToPaymentRepository(payment);
    }

/**    =========================================================   */

/**    여기서부터는 예약을 수정하기 위한 영역입니다.     */
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

/**  여기서부터는 예약이 실제로 있는지 확인하고, 요청자와 예약생성자의 신원이 동일한지 검증합니다.**/
    @Transactional(readOnly = true)
    public Reservation getReservations(Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        verifyReservationMember(reservation.getMember());

        return reservation;
    }
/**    =========================================================   */
/**  여기서부터는 예약 취소를 담당합니다. **/
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelReservation(Long reservationId) {
        Reservation findReservation = findReservation(reservationId);

        //로그인한 회원 정보와 reservation의 member와 동일한지 검증
        verifyReservationMember(findReservation.getMember());
        //예약 대기 -> 예약 취소
        findReservation.setReservationStatus(Reservation.ReservationStatus.RESERVATION_CANCEL);

        //결제 취소
        long cancelAmount = findReservation.getTotalPrice();
        paymentService.refund(findReservation.getPaymentKey(), cancelAmount);
        findReservation.setReservationStatus(Reservation.ReservationStatus.RESERVATION_CANCEL);
        reservationRepository.save(findReservation);

        //Todo 예약 취소 -> 환불
    }

/**  여기서부터는 reservationItem 데이터를 생성합니다.  **/
    private List<ReservationItem> generateReservationItem(Reservation reservation, List<ReservationItem> reservationItems) {
        List<ReservationItem> newReservationItems = new ArrayList<>();

        for (ReservationItem reservationItem : reservationItems) {
            if (reservationItem.getTicketCount()==0) continue;
            reservationItem.setReservation(reservation);
            newReservationItems.add(reservationItem);
        }

        return reservationItemRepository.saveAll(newReservationItems);
    }
/**  여기서부터는 잔여티켓 계산을 담당합니다.  **/
    private int getRemainingTicketCount(Item item, Map<Long,Integer> reservationTickets) {
        int reservationTicketCount = // 예약된 티켓이 없다면 null로 나오므로, null과 int는 연산이 불가능하므로, int로 변환
                reservationTickets.containsKey(item.getItemId())
                        ? reservationTickets.get(item.getItemId()) : 0;

        int remainingTicketCount = item.getTotalTicket() - reservationTicketCount;
        return remainingTicketCount;
    }

/**  여기서부터는 그 날짜에, 그 스토어의 아이템들 정보를 가져오는 기능을 담당합니다.  **/
    public List<ItemDto> findItemsByStoreIdAndDate(long storeId, String targetDate) {
            //Todo 예외처리하기
            LocalDate date;
            try{
                date = LocalDate.parse(targetDate, DateTimeFormatter.BASIC_ISO_DATE);
            }catch (DateTimeParseException e) {
                throw new BusinessLogicException(ExceptionCode.DATE_BAD_REQUEST);
            }

            List<ItemDto> itemDtos = new ArrayList<>();
            Store findStore = storeService.findStoreByStoreId(storeId);
            List<Item> findItems = findStore.getItems();
            Map<Long, Integer> reservationTickets = reservationTicketCount(findStore, date);
            for (Item item : findItems) {
                if (item.getStatus().equals(Item.ItemStatus.DELETED)) continue;
                int remainingTicketCount = getRemainingTicketCount(item,reservationTickets);
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
    }
    /**    여기서부터는 선택한 날짜와 스토어에 속해있는 아이템들을 대상으로 몇 개의 예약이 적용되어있는지 확인하는 역할입니다. remainingTicket 을 계산하는데 사용됩니다.    */
    public Map<Long, Integer> reservationTicketCount(Store store, LocalDate currentDate) throws BusinessLogicException {
        //Todo 리펙토링하기
        //선택한 업체와 날짜의 예약들을 가져옴
        List<Reservation> currentDateReservations =
                reservationRepository.findByReservationDateAndStore(currentDate, store);
        Map<Long, Integer> remainingTicketInfo = new HashMap<>();
        for (Reservation reservation : currentDateReservations) {
            //취소된 예약은 셈하지 않는다.
            if (reservation.getReservationStatus().equals(Reservation.ReservationStatus.RESERVATION_CANCEL)) continue;
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

    /**    여기서부터는 예약을 생성한 사람과, 요청자가 동일한지 확인합니다.    */
    private Member verifyReservationMember(Member reservationMember) {
        String loginUserEmail = AuthUtil.getCurrentMemberEmail();
        Member findMember = memberService.findMemberByEmail(loginUserEmail);

        if (!findMember.equals(reservationMember)) {
            throw new BusinessLogicException(ExceptionCode.RESERVATION_MEMBER_NOT_FOUND);
        }

        return findMember;
    }

    /**    reservationId를 이용해서 예약내역을 찾습니다.  */
    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.RESERVATION_NOT_FOUND));
    }

    /**    여기서부터는 예약날짜의 검증을 담당합니다. 예를 들어, 어제 날짜로 예약하게 되면 검증에 실패합니다.   */
    private void validateReservationDate(LocalDate reservationDate) {
        // 오늘 기준 이전 날짜가 오면 에러 발생
        LocalDate today = LocalDate.now();

        if (reservationDate.isBefore(today)) {
            throw new BusinessLogicException(ExceptionCode.INVALID_RESERVATION_DATE);
        }
    }

/**    여기서부터는 예약요청 상품의 총 금액과, 실제 결제되어야 하는 금액을 비교하게 됩니다. 예를 들어 실제 결제 되어야 하는 금액이 100원인데,
        50원으로 예약요청이 들어오는 경우 검증에 실패하게 됩니다. */
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

/**    여기서부터는 예약하려는 티켓 수량이, 잔여 티켓을 초과하는지 여부를 검증하게 됩니다.    */
    private void validateTicketCount(Store store,LocalDate date, List<ReservationItem> requestItems) {
        Map<Long, Integer> reservationTickets = reservationTicketCount(store, date);
        for (int i=0; i< store.getItems().size(); i++) {
            Item item = store.getItems().get(i);
            int requestTicketCount = requestItems.get(i).getTicketCount();
            int remainingTicketCount = getRemainingTicketCount(item,reservationTickets);
            if (requestTicketCount > remainingTicketCount)
                throw new BusinessLogicException(ExceptionCode.TICKET_QUANTITY_EXCEEDED);
        }
    }


/**    여기서부터는 리뷰를 작성할 자격이 있는지 검증하기 위해, 사용자가 해당 스토어에 몇개의 예약을 했는지 확인하기 위한 기능과 관련이 있습니다.
        예를 들어, 예약을 2개를 했다면 사용자는 2개의 리뷰를 남길 수 있습니다. */
    public int countReservation(Store store, Member member) {
        int count = reservationRepository.
                countByStoreAndMemberAndReservationStatus(store,
                        member,Reservation.ReservationStatus.RESERVATION_USE_COMPLETED);
        return count;
    }


/**    여기서부터는 예약 상태를, 사용완료로 변경하는 역할과 관련이 있습니다.    */
    public void changeReservationStatus(long reservationId) {
        Reservation findReservation = findReservation(reservationId);
        findReservation.setReservationStatus(Reservation.ReservationStatus.RESERVATION_USE_COMPLETED);
    }


/**  여기서부터는 아이템을 검증합니다. **/
    private void validateItemId(List<ReservationItem> reservationItems, Store store) {
        List<Item> items = store.getItems();
        for (ReservationItem reservationItem : reservationItems) {
            if (!items.contains(reservationItem.getItem()))
                throw new BusinessLogicException(ExceptionCode.REQUEST_ITEM_ID_IS_REJECTED);
        }
    }
/**    여기서부터는 예약 레코드를 추가하기 위해, reservation Entity를 생성하게 됩니다.    */
    public Reservation createReservationEntity(Long storeId, Reservation reservation,
                                               List<ReservationItem> reservationItems) {
        //로그인한 유저의 정보를 reservation에 담기 겸, 사용자 검증
        Member member = memberService.findMemberByEmail(AuthUtil.getCurrentMemberEmail());
        reservation.setMember(member);
        //스토어 가져오기 겸 검증
        Store store = storeService.findStoreByStoreId(storeId);
        validateItemId(reservationItems, store);
        reservation.setStore(store);
        //예약 날짜 유효성 검사
        validateReservationDate(reservation.getReservationDate());
        validateTicketCount(store,reservation.getReservationDate(), reservationItems);
        // reservationItem 저장
        List<ReservationItem> newReservationItems = generateReservationItem(reservation, reservationItems);
        reservation.setReservationItems(newReservationItems);
        // 입력된 총 금액과 총 예약 가격이 동일한지 검증
        validateTotalPrice(newReservationItems, reservation.getTotalPrice());
        return reservation;
    }
}