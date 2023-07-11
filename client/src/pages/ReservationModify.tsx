import { reservation } from '../dummy/reservation';
import {
  ReservationContainer,
  ReservationTitle,
  Title,
  SubTitle,
  ReservationInfo,
  InfoTitle,
  ReservationInput,
  InputRequire,
  PaymentInfoBox,
  AmountBox,
  RuleBox,
  PaymentButton,
} from '../styles/Reservation/ReservationModify';

function ReservationModify() {
  return (
    <div className="flex">
      <ReservationContainer>
        <ReservationTitle>{reservation.storeName}</ReservationTitle>
        <Title>예약정보</Title>
        <div className="ml-3">
          <SubTitle>1. 결제한 상품</SubTitle>
          {reservation.reservationItems.map((el) => {
            return (
              <div className="mb-3" key={el.item}>
                {el.item} x <span>{el.ticketCount}</span>
              </div>
            );
          })}
          <SubTitle>2. 예약 날짜</SubTitle>
          {reservation.reservationDate}
          <ReservationInfo>
            <Title>예약자 정보</Title>
            <button className="relative left-[520px] bottom-[50px] bg-[#F3F5F7] rounded-lg font-semibold text-[14px] p-[10px]">
              수정하기
            </button>
            <div className="flex ml-7 mb-3 items-center">
              <InfoTitle>예약자</InfoTitle>
              <div className="relative">
                <ReservationInput
                  type="text"
                  placeholder={reservation.reservationName}
                />
                <InputRequire>필수</InputRequire>
              </div>
            </div>
            <div className="flex ml-7 mb-3 items-center">
              <InfoTitle>연락처</InfoTitle>
              <div className="relative">
                <ReservationInput
                  type="text"
                  placeholder={reservation.reservationPhone}
                />
                <InputRequire>필수</InputRequire>
              </div>
            </div>
            <div className="flex ml-7 mb-3 items-center">
              <InfoTitle>이메일</InfoTitle>
              <ReservationInput
                type="text"
                placeholder={reservation.reservationEmail}
              />
            </div>
          </ReservationInfo>
        </div>
      </ReservationContainer>
      <PaymentInfoBox>
        <div className="m-5 pb-3 text-lg border-b-[1px] border-black">
          결제정보
        </div>
        <div className="m-7 flex justify-between">
          <div>주문금액</div>
          <div>{reservation.totalPrice.toLocaleString('ko-KR')} 원</div>
        </div>
        <AmountBox>
          <div>총 결제금액</div>
          <div>{reservation.totalPrice.toLocaleString('ko-KR')} 원</div>
        </AmountBox>
        <RuleBox>
          <div className="font-semibold">예약취소 규정</div>
          <ul className="text-xs list-disc mt-3 pl-5">
            <li>체험일 4일전 18시이전 100% 환불가능</li>
            <li>체험일 4일전 18시이후~당일 : 환불불가, 날짜 변경 불가</li>
            <li>부분 사용 및 부분 취소는 불가능합니다.</li>
          </ul>
        </RuleBox>
        <PaymentButton type="button">예약취소</PaymentButton>
      </PaymentInfoBox>
    </div>
  );
}

export default ReservationModify;
