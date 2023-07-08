package actiOn.Img.storeImg;

import actiOn.Img.profileImg.ProfileImg;
import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreImgRepository extends JpaRepository<StoreImg,Long> {
    Optional<StoreImg> findByStore(Store store);
}
