package actiOn.review.entity;

import actiOn.audit.BaseEntity;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Review extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "REVIEW_ID")
    private Long reviewId;

    @Column(name = "CONTENT",nullable = false)
    private String content;

    @Min(0) @Max(5)
    @Column(name = "RATING",nullable = false)
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;
}
