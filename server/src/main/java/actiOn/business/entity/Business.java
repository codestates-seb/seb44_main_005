package actiOn.business.entity;

import actiOn.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@Entity(name = "BUSINESS")
public class Business extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "BUSINESS_ID")
    private Long businessId;

    @Column(name = "BUSINESS_NAME", nullable = false, length = 50)
    private String businessName;

    @Column(name = "REGISTRATION_NUMBER",nullable = false)
    private String registrationNumber;

    @Column(name = "BUSINESS_CATEGORY", nullable = false)
    private String businessCategory;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
