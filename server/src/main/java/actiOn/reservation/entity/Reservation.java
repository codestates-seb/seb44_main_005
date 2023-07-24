package actiOn.reservation.entity;


import actiOn.helper.audit.BaseEntity;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SQLDelete(sql = "UPDATE RESERVATION SET deleted_at = CURRENT_TIMESTAMP where reservation_id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;

    @Column(nullable = false)
    private String reservationName;

    @Column(nullable = false)
    private String reservationPhone;

    @Column(nullable = false)
    private String reservationEmail;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    private int totalPrice;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private String paymentKey;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING) // enum 추가
    private ReservationStatus reservationStatus = ReservationStatus.RESERVATION_REQUEST;

    @ManyToOne(fetch = FetchType.LAZY) //성능을 최적화 하기 위해 사용, 연관된 엔티티를 사용하는 시점에 가져옴
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.PERSIST)
    private List<ReservationItem> reservationItems = new ArrayList<>();

    public enum ReservationStatus {
        RESERVATION_REQUEST(1, "예약 대기"),
        RESERVATION_CONFIRM(2, "예약 확정"), //결제 후 예약 확정
        RESERVATION_USE_COMPLETED(3, "이용 완료"),  // 결제 및 예약 날짜 다음에 이용 완료 처리?? // todo 예약 시간이 현재 시간 보다 크면 이용 완료 처리
        RESERVATION_CANCEL(4, "예약 취소");

        @Getter
        private int stepNumber;

        @Getter
        private String stepDescription;

        ReservationStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }

    public Reservation(String reservationName, String reservationPhone, String reservationEmail) {
        this.reservationName = reservationName;
        this.reservationPhone = reservationPhone;
        this.reservationEmail = reservationEmail;
    }
}
