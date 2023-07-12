package actiOn.member.entity;

import actiOn.Img.profileImg.ProfileImg;
import actiOn.auth.role.MemberRole;
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
import java.util.stream.Collectors;

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

    @Column
    private String phoneNumber;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH})
    private List<MemberRole> memberRoles = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private Business business;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Wish> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Store> stores = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private ProfileImg profileImg;

    @Override
    public String getName() {
        return getEmail();
    }

    public List<String> getRoles() {
        return this.getMemberRoles()
                .stream()
                .map(memberRole -> memberRole.getRole().getName())
                .collect(Collectors.toList());
    }
}
