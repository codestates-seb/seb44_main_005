package actiOn.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class MemberReservationResponseDto {
    List<MemberReservationDto> data;

    @Builder
    @Getter
    public static class MemberReservationDto {
        private Long storeId;
        private String kakao;
        private String storeImg;
        private String storeName;
        private LocalDate reservationDate;
        private int totalPrice;
        private int itemCount;
        private String reservationStatus;
    }
}
