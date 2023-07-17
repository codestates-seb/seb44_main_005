import { reservationCheckdummy } from "../dummy/reservationcheckdummy";
import {
  ResCheckContainer,
  ResCheckTitle,
  ResCheckCards,
  ResImgContainer,
  ImgResSzing,
  ImgStyle,
  ResInfoContainer,
  StatusContainer,
  ResInformation,
  ResDate,
  ResItemCount,
  ResTotalPrice,
  ResButtonsContainer,
  ButtonStyle,
  NoButtons,
} from '../styles/MyPage/ReservationCheck';

function ReservationCheck() {
  const { data } = reservationCheckdummy;
  
  return (
    <ResCheckContainer>
      <ResCheckTitle>예약 내역 조회</ResCheckTitle>
      {data.map((reservation) => (
        <ResCheckCards key={reservation.storeId}>
          <ResImgContainer>
            <ImgResSzing>
              <ImgStyle src={reservation.storeImage} alt="reservation image" />
            </ImgResSzing>
          </ResImgContainer>
          <ResInfoContainer>
            <StatusContainer>
              <div 
                className={
                  `
                    font-medium
                    text-[15px]
                    ${reservation.reservationStatus === "예약 완료" 
                      ? "bg-[#4771B7] text-white"
                      : reservation.reservationStatus === "이용 완료"
                      ? "bg-white text-[#4771B7] border-[1px] border-[#4771B7]"
                      : "bg-[#DD3535] text-white"
                    } 
                    w-[77px]
                    h-[27px]
                    flex
                    justify-center
                    items-center
                  `
                }
              >
                <span>{reservation.reservationStatus}</span>
              </div>
            </StatusContainer>
            <ResInformation>
              <div>
                <span className="text-[16px]">{reservation.reservationDate}</span>
              </div>
              <div className="space-x-3">
                <ResDate>{reservation.storeName}</ResDate>
                <ResItemCount>총 {reservation.itemCount}개 상품 결제</ResItemCount>
              </div>
              <div>
                <ResTotalPrice>결제금액: {reservation.totalPrice}원</ResTotalPrice>
              </div>
            </ResInformation>
            <ResButtonsContainer>
              {reservation.reservationStatus === "예약 완료" && (
                <div className="space-x-3">
                  <ButtonStyle type="button">예약 수정</ButtonStyle>
                  <ButtonStyle type="button">예약 삭제</ButtonStyle>
                </div>
              )}
              {(reservation.reservationStatus === "예약 취소" || reservation.reservationStatus === "이용 완료") && (
                <div>
                  <NoButtons></NoButtons>
                </div>
              )}
            </ResButtonsContainer>
          </ResInfoContainer>
        </ResCheckCards>
      ))}
    </ResCheckContainer>
  );
}

export default ReservationCheck;
