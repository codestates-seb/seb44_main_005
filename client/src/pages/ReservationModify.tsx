import { useEffect, useState } from 'react';
// import { reservation } from '../dummy/reservation';
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
  InputContainer,
  ModifyButton,
} from '../styles/Reservation/ReservationModify';
import { useNavigate, useSearchParams } from 'react-router-dom';

type CProps = {
  storeName: string;
  reservationDate: string;
  totalPrice: number;
  reservationName: string;
  reservationPhone: string;
  reservationEmail: string;
  reservationItems: any[];
};

function ReservationModify() {
  const url = import.meta.env.VITE_APP_API_URL;
  const initialReservation = {
    storeName: '',
    reservationDate: '',
    totalPrice: 0,
    reservationName: '',
    reservationPhone: '',
    reservationEmail: '',
    reservationItems: [],
  };

  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const reservationId = searchParams.get('reservationId');

  const [data, setData] = useState<CProps>(initialReservation);
  const [name, setName] = useState('');
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');

  const HandlerName = (e) => {
    setName(e.currentTarget.value);
  };
  const HandlerPhone = (e) => {
    setPhone(e.currentTarget.value);
  };
  const HandlerEmail = (e) => {
    setEmail(e.currentTarget.value);
  };

  //예약자 정보변경
  const handleModify = () => {
    try {
      fetch(`${url}/reservations/${reservationId}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          Authorization: sessionStorage.getItem('Authorization')
        },
        body: JSON.stringify({
          reservationName: name,
          reservationPhone: phone,
          reservationEmail: email,
        }),
      });
      alert('수정이 완료 되었습니다');
      location.reload();
    } catch (error) {
      console.error(error);
    }
  };

  //예약 취소
  const handleCancel = () => {
    try {
      fetch(`${url}/reservations/${reservationId}`, {
        method: 'DELETE',
        headers: { Authorization: sessionStorage.getItem('Authorization') },
      });
      navigate('/my/order'); // 예약내역 조회 페이지로 이동
      window.location.reload();
    } catch (error) {
      console.error(error);
    }
  };

  //Get
  useEffect(() => {
    const fetchData = () => {
      fetch(`${url}/reservations/${reservationId}`, {
        headers: { Authorization: sessionStorage.getItem('Authorization') },
      })
        .then((res) => res.json())
        .then((data) => {
          setData(data);
          setName(data.reservationName);
          setPhone(data.reservationPhone);
          setEmail(data.reservationEmail);
        });
    };
    fetchData();
  }, [reservationId]);

  return (
    <div className="flex justify-center">
      <ReservationContainer>
        <ReservationTitle>{data.storeName}</ReservationTitle>
        <Title>예약정보</Title>
        <SubTitle>1. 결제한 상품</SubTitle>
        {data.reservationItems.map((el) => {
          return (
            <li className="mb-3 list-none" key={el.itemName}>
              {el.itemName} x <span>{el.ticketCount}</span>
            </li>
          );
        })}
        <SubTitle>2. 예약 날짜</SubTitle>
        {data.reservationDate}
        <ReservationInfo>
          <Title>예약자 정보</Title>
          <ModifyButton onClick={handleModify}>수정하기</ModifyButton>
          <InputContainer>
            <InfoTitle>예약자</InfoTitle>
            <div className="relative">
              <ReservationInput
                type="text"
                value={name}
                onChange={HandlerName}
              />
              <InputRequire>필수</InputRequire>
            </div>
          </InputContainer>
          <InputContainer>
            <InfoTitle>연락처</InfoTitle>
            <div className="relative">
              <ReservationInput
                type="tel"
                value={phone}
                onChange={HandlerPhone}
                maxLength={13}
                placeholder="010-0000-0000"
              />
              <InputRequire>필수</InputRequire>
            </div>
          </InputContainer>
          <InputContainer>
            <InfoTitle>이메일</InfoTitle>
            <ReservationInput
              type="email"
              value={email}
              onChange={HandlerEmail}
            />
          </InputContainer>
        </ReservationInfo>
      </ReservationContainer>
      <PaymentInfoBox>
        <div className="m-5 pb-3 text-lg border-b-[1px] border-black">
          결제정보
        </div>
        <div className="m-7 flex justify-between">
          <div>주문금액</div>
          <div>{data.totalPrice.toLocaleString('ko-KR')} 원</div>
        </div>
        <AmountBox>
          <div>총 결제금액</div>
          <div>{data.totalPrice.toLocaleString('ko-KR')} 원</div>
        </AmountBox>
        <RuleBox>
          <div className="font-semibold">예약취소 규정</div>
          <ul className="text-xs list-disc mt-3 pl-5">
            <li>체험일 4일전 18시이전 100% 환불가능</li>
            <li>체험일 4일전 18시이후~당일 : 환불불가, 날짜 변경 불가</li>
            <li>부분 사용 및 부분 취소는 불가능합니다.</li>
          </ul>
        </RuleBox>
        <PaymentButton type="button" onClick={handleCancel}>
          예약취소
        </PaymentButton>
      </PaymentInfoBox>
    </div>
  );
}

export default ReservationModify;
