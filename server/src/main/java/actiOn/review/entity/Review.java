package actiOn.review.entity;

import actiOn.audit.BaseEntity;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Review extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long reviewId;

    @Column(nullable = false)
    private String content;

    @Range(min = 0, max = 5)
    @Column(nullable = false)
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    public Review(String content, Integer rating){
        this.content = content;
        this.rating = rating;
    }
}
