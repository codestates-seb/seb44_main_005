package actiOn.Img.storeImg;

import actiOn.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreImgRepository extends JpaRepository<actiOn.Img.storeImg.StoreImg,Long> {
    Optional<actiOn.Img.storeImg.StoreImg> findByStore(Store store);

    Optional<StoreImg> findByLink(String link);
}
