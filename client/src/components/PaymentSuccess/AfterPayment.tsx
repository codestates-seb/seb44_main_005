import { Link, useSearchParams } from 'react-router-dom';
import { BsCheckLg } from 'react-icons/bs';
import { IoClose } from 'react-icons/io5';

import {
  AfterPaymentBtn,
  AfterPaymentContent,
  AfterPaymentSection,
  CheckIcon,
  CloseIcon
} from '../../styles/PaymentSuccess/AfterPayment';

function AfterPayment({ isSuccess }) {
  const [searchParams] = useSearchParams();
  const reservationNumber = searchParams.get('orderId');
  const amount = searchParams.get('amount');
  const storeId = searchParams.get('storeId');

  return (
    <AfterPaymentSection>
      {isSuccess ?
        <CheckIcon>
          <BsCheckLg size="100" color="white"/>
        </CheckIcon> :
        <CloseIcon>
          <IoClose size="100" color="white"/>
        </CloseIcon>
      }
      <div className="pt-5 text-xl font-semibold">예약에 {isSuccess ? '성공' : '실패'}하였습니다.</div>
      <AfterPaymentContent>
        <div className="mb-2">예약번호: {reservationNumber}</div>
        <div>결제금액: {Number(amount).toLocaleString()}원</div>
      </AfterPaymentContent>
      {isSuccess ?
        <Link to="/my/order">
          <AfterPaymentBtn>결제완료</AfterPaymentBtn>
        </Link> :
        <Link to={`/category/${storeId}`}>
          <AfterPaymentBtn>확인</AfterPaymentBtn>
        </Link>
      }
    </AfterPaymentSection>
  );
}

export default AfterPayment;
