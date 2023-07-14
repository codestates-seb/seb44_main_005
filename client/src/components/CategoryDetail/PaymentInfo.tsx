import { useRecoilValue } from 'recoil';
import { useLocation } from 'react-router-dom';

import { CategoryDetailState, ReserFormState, totalPrice } from '../../store/categoryDetailAtom';
import {
  AmountBox,
  InquiryBox,
  PaymentButton,
  PaymentInfoBox,
  RuleBox,
  StoreProfile,
  WishButton
} from '../../styles/CategoryDetail/PaymentInfo';

function PaymentInfo() {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const data = useRecoilValue(CategoryDetailState);
  const form = useRecoilValue(ReserFormState);
  const total = useRecoilValue(totalPrice);
  const location = useLocation();

  const reservationPost = async () => {
    const storeId = location.pathname.substring(10);
    try {
      await fetch(`${API_URL}/reservations/${storeId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(form)
      })
    }
    catch(error) {
      console.log(error);
    }
  }
  
  return (
    <PaymentInfoBox>
      <div>
        <div className="m-5 pb-3 text-lg border-b-[1px] border-black">
          결제정보
        </div>
        <div className="m-7 flex justify-between">
          <div>주문금액</div>
          <div>{total}원</div>
        </div>
        <AmountBox>
          <div>총 결제금액</div>
          <div>{total}원</div>
        </AmountBox>
        <RuleBox>
          <div className="font-semibold">예약취소 규정</div>
          <ul className="text-xs list-disc mt-3 pl-5">
            <li>체험일 4일전 18시이전 100% 환불가능</li>
            <li>체험일 4일전 18시이후~당일 : 환불불가, 날짜 변경 불가</li>
            <li>부분 사용 및 부분 취소는 불가능합니다.</li>
          </ul>
        </RuleBox>
        <PaymentButton type="button" onClick={reservationPost}>{total}원 결제하기</PaymentButton>
        <WishButton type="button">
          <span className="text-[#4771B7]">♥ </span>
          <span>위시리스트에 담기</span>
        </WishButton>
      </div>
      <InquiryBox>
        <StoreProfile src={data.profileImg} alt="업체 프로필" />
        <div>
          <div className="font-semibold">{data.storeName}</div>
          <div className="text-xs">[문의] 카카오톡 아이디: {data.kakao}</div>
        </div>
      </InquiryBox>
    </PaymentInfoBox>
  );
}

export default PaymentInfo;
