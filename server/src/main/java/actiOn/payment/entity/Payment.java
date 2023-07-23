package actiOn.payment.entity;

import actiOn.helper.audit.BaseEntity;
import actiOn.member.entity.Member;
import actiOn.reservation.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue
    private Long paymentId;

    @Column
    private String paymentKey;

    @Column
    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Column
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Column(nullable = false)
    private Long totalAmount;

    @Column(nullable = false)
    private String orderId;

    @Column
    private String orderName;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status = Status.READY;

    @Column
    private LocalDateTime requestedAt;

    @Column
    private LocalDateTime approvedAt;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member customer;

    @OneToOne(mappedBy = "payment")
    private PaymentCancel cancel;

    public enum PayType {
        NORMAL,
        BILLING,
        BRANDPAY
    }

    public enum PayMethod {
        카드, 가상계좌, 간편결제, 계좌이체
    }

    public enum Status {
        READY,
        IN_PROGRESS,
        WAITING_FOR_DEPOSIT,
        DONE,
        CANCELED,
        PARTIAL_CANCELED,
        ABORTED,
        EXPIRED
    }
}
