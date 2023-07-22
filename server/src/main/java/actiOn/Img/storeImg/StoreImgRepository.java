package actiOn.Img.storeImg;

import actiOn.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StoreImgRepository extends JpaRepository<StoreImg, Long> {

    Optional<StoreImg> findByLink(String link);

    StoreImg findByStoreAndIsThumbnail(Store store, boolean isThumbnail);

    int countByStore(Store store);
}
