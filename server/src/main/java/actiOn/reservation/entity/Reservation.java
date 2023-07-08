package actiOn.reservation.entity;


import actiOn.helper.audit.BaseEntity;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Date reservationDate;

    @Enumerated(EnumType.STRING) // enum 추가
    private ReservationStatus reservationStatus = ReservationStatus.RESERVATION_REQUEST;

    // payment 아직 없으니 일단 주석처리
 /*   @OneToOne
    @JoinColumn(name = "PAYMENT_ID")
    private Payment payment;*/

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.PERSIST)
    private List<ReservationItem> reservationItems = new ArrayList<>();

    public enum ReservationStatus {
        RESERVATION_REQUEST(1, "예약 대기"),
        RESERVATION_CONFIRM(2, "예약 확정"),
        RESERVATION_CANCLE(4, "예약 취소");

        @Getter
        private int stepNumber;

        @Getter
        private String stepDescription;

        ReservationStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }

    }

    // 다대다 관계를 위한 편의 메서드
    public void addReservationItem(ReservationItem reservationItem) {
        reservationItems.add(reservationItem);
        reservationItem.setReservation(this);
    }

    public void removeReservationItem(ReservationItem reservationItem) {
        reservationItems.remove(reservationItem);
        reservationItem.setReservation(null);
    }

}
