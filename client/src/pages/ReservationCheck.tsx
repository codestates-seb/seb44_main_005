import { useEffect, useState } from "react";
import{ GiBoatHorizon } from 'react-icons/gi';
import NothingComponent from "../components/MyPage/NothingComponent";
import LoadingComponent from '../components/Loading/LoadingComponent';
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
  NoReservation,
  ButtonReview,
} from '../styles/MyPage/ReservationCheck';
import { Link } from "react-router-dom";

function ReservationCheck() {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const accessToken = sessionStorage.getItem('Authorization');
  const reservationDelete = async (reservationId: number) => {
    const confirmDelete = confirm("정말 예약을 취소하겠습니까?");
    const res = confirmDelete && await fetch(`${API_URL}/reservations/${reservationId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': accessToken
      }
    });
    if (res.status === 204) {
      window.location.reload();
    }
  }

  const reservationListFetch = async () => {
    const res = await fetch(`${API_URL}/mypage/reservations`, {
      method: 'GET',
      headers: {
        'Authorization': accessToken
      }
    });
    const json = await res.json();
    setData(json.data);
    setLoading(false);
  }

  useEffect(() => {
    reservationListFetch();
  }, [])
  
  return (
    <ResCheckContainer>
      <ResCheckTitle>예약 내역 조회</ResCheckTitle>
      {loading ? (
          <div className='flex flex-col justify-center items-center h-[800px] w-[902px]'>
            <LoadingComponent />
          </div>
      ) : data.length === 0 ? (
        <NoReservation>
          <GiBoatHorizon style={{ fontSize: '100px', color: '#4771B7'}} />
          <NothingComponent 
            title='아직 등록된 예약이 없네요!'
            description='관심가는 상품을 찾아 예약을 해볼까요?'
          />
        </NoReservation>
      ) : (
        data && data.map((reservation, idx) => (
          <ResCheckCards key={idx}>
            <ResImgContainer>
              <ImgResSzing>
                <ImgStyle src={reservation.storeImg} alt="reservation image" />
              </ImgResSzing>
            </ResImgContainer>
            <ResInfoContainer>
              <StatusContainer>
                <div 
                  className={
                    `
                      font-medium
                      text-[15px]
                      pt-[1px]
                      ${reservation.reservationStatus === "예약 확정" 
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
                  <Link to={`/category/${reservation.storeId}`}><ResDate>{reservation.storeName}</ResDate></Link>
                  <ResItemCount>총 {reservation.itemCount}개 상품 결제</ResItemCount>
                </div>
                <div>
                  <ResTotalPrice>결제금액: {Number(reservation.totalPrice).toLocaleString()}원</ResTotalPrice>
                </div>
              </ResInformation>
              <ResButtonsContainer>
                {reservation.reservationStatus === "예약 확정" && (
                  <div className="space-x-3">
                    <Link to={`/my/order/edit?reservationId=${reservation.reservationId}`}>
                      <ButtonStyle type="button">상세 확인</ButtonStyle>
                    </Link>
                    <ButtonStyle
                      type="button"
                      onClick={() => {reservationDelete(reservation.reservationId)}}
                    >예약 취소</ButtonStyle>
                  </div>
                )}
                {reservation.reservationStatus === "예약 취소" && (
                  <div>
                    <NoButtons></NoButtons>
                  </div>
                )}
                {reservation.reservationStatus === "이용 완료" && (
                  <Link to={`/category/${reservation.storeId}`}>
                    <ButtonReview>리뷰 작성</ButtonReview>
                  </Link>
                )}
              </ResButtonsContainer>
            </ResInfoContainer>
          </ResCheckCards>
        ))
      )}
    </ResCheckContainer>
  );
}

export default ReservationCheck;
