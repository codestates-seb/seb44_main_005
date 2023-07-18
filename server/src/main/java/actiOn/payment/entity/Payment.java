package actiOn.payment.entity;

import actiOn.helper.audit.BaseEntity;
import actiOn.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Column(nullable = false)
    private Long totalAmount;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String orderName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.READY;

    @Column(nullable = false)
    private String requestedAt;

    @Column(nullable = false)
    private String approvedAt;

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
