package actiOn.store.entity;

import actiOn.business.entity.Business;
import actiOn.item.entity.Item;
import actiOn.reservation.entity.Reservation;
import actiOn.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Store extends Auditable {
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

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int likeCount;

    @OneToOne(mappedBy = "store", cascade = CascadeType.REFRESH)
    private List<Business> businesses = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    public void setBusinesses(Business business) {

    }

    public void setReservations(Reservation reservation) {
        this.reservations.add(reservation);
        if (reservation.getStore() != this) {
            reservation.setStore(this);
        }
    }

    public void setItems(Item item) {
        this.items.add(item);
        if (item.getStore() != this) {
            item.setStore(this);
        }
    }

    public void setReviews(Review review) {
        this.reviews.add(review);
        if (review.getStore() != this) {
            review.setStore(this);
        }
    }
}
