package actiOn.member.entity;

import actiOn.Img.profileImg.ProfileImg;
import actiOn.business.entity.Business;
import actiOn.helper.audit.BaseEntity;
import actiOn.store.entity.Store;
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
public class Member extends BaseEntity implements Principal {
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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private Business business;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Wish> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Store> stores = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private ProfileImg profileImg;

    @Override
    public String getName() {
        return getEmail();
    }

    public enum MemberRole {
        ROLE_USER,
        ROLE_PARTNER
    }
}
