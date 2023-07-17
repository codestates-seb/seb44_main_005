package actiOn.Img.profileImg;

import actiOn.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImgRepository extends JpaRepository<actiOn.Img.profileImg.ProfileImg, Long> {
    Optional<ProfileImg> findByMemberAndImgStatus(Member member, ProfileImg.ProfileImgStatus status);
}
