package actiOn.member.entity;

import actiOn.business.entity.Business;
import actiOn.wish.entity.Wish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member implements Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Business> businesses = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Wish> wishList = new ArrayList<>();

    public void addBusinesses(Business business) {
        this.businesses.add(business);
        if (business.getMember() != this) {
            business.setMember(this);
        }
    }

    public void addWishList(Wish wish) {
        this.wishList.add(wish);
        if (wish.getMember() != this) {
            wish.setMember(this);
        }
    }
}
