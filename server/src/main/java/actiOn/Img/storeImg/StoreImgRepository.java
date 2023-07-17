package actiOn.Img.storeImg;

import actiOn.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface StoreImgRepository extends JpaRepository<actiOn.Img.storeImg.StoreImg,Long> {
    Optional<actiOn.Img.storeImg.StoreImg> findByStore(Store store);

    Optional<StoreImg> findByLink(String link);

    StoreImg findByStoreAndIsThumbnail(Store store, boolean isThumbnail);

    void deleteByLink(String link);
    int countByStore(Store store);
}
