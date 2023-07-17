package actiOn.Img.storeImg;


import actiOn.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class StoreImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imgId;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private Boolean isThumbnail = false;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    public StoreImg(String link, Store store) {
        this.link = link;
        this.store = store;
    }


}
