package actiOn.member.entity;

import actiOn.Img.profileImg.ProfileImg;
import actiOn.auth.role.MemberRole;
import actiOn.business.entity.Business;
import actiOn.helper.audit.BaseEntity;
import actiOn.reservation.entity.Reservation;
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

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToOne(mappedBy = "member", fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
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

    // 클라이언트에 회원 권한 전송하기 위한 메서드
    public String getRoleName() {
        List<String> roleNames = this.getRoles();

        // 파트너 권한이 포함되어 있으면 파트너로 리턴
        if (roleNames.contains("PARTNER")) {
            return "PARTNER";
        }

        return "USER";
    }

    // 프로필 이미지 링크 반환
    public String getProfileImgLink() {
        // 프로필 사진 null인 경우 기본 이미지 링크 리턴
        if (this.getProfileImg() == null) {
            return "default image";
        }

        return this.getProfileImg().getLink();
    }
}
