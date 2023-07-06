package actiOn.business.entity;

import actiOn.audit.BaseEntity;
import actiOn.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Business extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessId;

    @Column(nullable = false, length = 50)
    private String businessName;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @Column(nullable = false)
    private String businessCategory;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
