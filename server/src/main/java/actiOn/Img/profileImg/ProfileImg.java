package actiOn.Img.profileImg;

import actiOn.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProfileImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imgId;

    @Column(nullable = false)
    private String link = "default image";

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ProfileImgStatus imgStatus = ProfileImgStatus.PROFILE_DEFAULT;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public enum ProfileImgStatus {
        PROFILE_DEFAULT,
        PROFILE_ACTIVE,
        PROFILE_DELETED
    }
}
//Todo 내 예약 조회는 memberController를 통해 요청을 받지만, 처리 로직은 reservationService에서 처리되어야 함