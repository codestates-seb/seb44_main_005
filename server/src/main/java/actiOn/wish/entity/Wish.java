package actiOn.wish.entity;

import actiOn.member.entity.Member;
import actiOn.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@Entity(name = "WISH")
public class Wish {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "WISH_ID")
    private Long wishId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "STORED_ID")
    private Store store;
}
