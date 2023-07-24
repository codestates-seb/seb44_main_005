package actiOn.store.entity;

import actiOn.Img.storeImg.StoreImg;
import actiOn.helper.audit.BaseEntity;
import actiOn.item.entity.Item;
import actiOn.member.entity.Member;
import actiOn.reservation.entity.Reservation;
import actiOn.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "STORE")
@SQLDelete(sql = "UPDATE STORE SET deleted_at = CURRENT_TIMESTAMP where store_id = ?")
@Where(clause = "deleted_at IS NULL")
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private String kakao;

    @Column(nullable = false)
    private String contact;

    @Column(columnDefinition = "integer default 0")
    private int likeCount;

    //리뷰 개수
    @Column(columnDefinition = "integer default 0")
    private int reviewCount;

    //평점
    @Column
    private double rating;

    //가격
    @Column(nullable = false)
    private int lowPrice;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "store")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<StoreImg> storeImgList = new ArrayList<>();

    public void addLikeCount() {
        this.likeCount++;
    }

    public void subLikeCount() {
        this.likeCount--;
    }

    public void addReviewCount() {
        this.reviewCount++;
    }
}
