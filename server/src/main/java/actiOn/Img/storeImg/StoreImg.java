package actiOn.Img.storeImg;


import actiOn.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class StoreImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imgId;

    @Column(nullable = false, unique = true)
    private String link;

    @Column(nullable = false)
    private Boolean isThumbnail;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
