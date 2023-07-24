import { useRecoilState, useRecoilValue } from 'recoil';
import { useLocation, useNavigate } from 'react-router-dom';

import defaultImg from '../../assets/profile.svg';
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
import { isLoginState } from '../../store/userInfoAtom';

function PaymentInfo() {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const [form, setForm] = useRecoilState(ReserFormState);
  const isLogin = useRecoilValue(isLoginState);
  const data = useRecoilValue(CategoryDetailState);
  const total = useRecoilValue(totalPrice);
  const location = useLocation();
  const navigate = useNavigate();
  const storeId = location.pathname.substring(10);

  const movePayment = async () => {
    if (!isLogin) {
      alert(`로그인 상태에서만 예약할 수 있습니다.`);
      return navigate('/login');
    }
    else if (!form.reservationName) {
      return alert('예약자를 입력해주세요.');
    }
    else if (!form.reservationPhone) {
      return alert('연락처를 입력해주세요.');
    }
    else if (!total) {
      return alert('티켓을 선택해주세요.');
    }
    else if (form.reservationPhone.substring(0, 3) !== '010') {
      return alert('연락처를 다시 확인해주세요.');
    }
    const items = form.reservationItems.filter((item) => item.ticketCount !== 0);
    setForm((prev) => ({...prev, reservationItems: items}));
    navigate(`/store/payment/${storeId}`);
  }

  const onClickHeart = async () => {
    if (!isLogin) {
      return alert(`로그인 상태에서만 등록할 수 있습니다.`);
    }
    if (data.isLike) {
      return alert('이미 위시리스트에 담겨 있습니다.');
    }
    const res = await fetch(`${API_URL}/stores/favorites/${storeId}`, {
      method: 'POST',
      headers: { Authorization: sessionStorage.getItem('Authorization') },
    });
    if (res.ok) {
      alert('❤️ 위시리스트에 추가되었습니다.');
      window.location.reload();
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
          <div>{total.toLocaleString('ko-KR')}원</div>
        </div>
        <AmountBox>
          <div>총 결제금액</div>
          <div>{total.toLocaleString('ko-KR')}원</div>
        </AmountBox>
        <RuleBox>
          <div className="font-semibold">예약취소 규정</div>
          <ul className="text-xs list-disc mt-3 pl-5">
            <li>체험일 4일전 18시이전 100% 환불가능</li>
            <li>체험일 4일전 18시이후~당일 : 환불불가, 날짜 변경 불가</li>
            <li>부분 사용 및 부분 취소는 불가능합니다.</li>
            <li className="text-red-600">가상으로만 결제되고 실제로 돈이 빠져나가지 않으니 안심하고 마음껏 테스트 해보시길 바랍니다.</li>
          </ul>
        </RuleBox>
        <PaymentButton type="button" onClick={movePayment}>{total.toLocaleString('ko-KR')}원 결제하기</PaymentButton>
        <WishButton type="button" onClick={onClickHeart}>
          <span className="text-[#4771B7]">♥ </span>
          <span>위시리스트에 담기</span>
        </WishButton>
      </div>
      <InquiryBox>
        <StoreProfile src={data.profileImg === 'default image' ? defaultImg : data.profileImg} alt="업체 프로필" />
        <div>
          <div className="font-semibold">{data.storeName}</div>
          <div className="text-xs">[문의] 카카오톡 아이디: {data.kakao}</div>
        </div>
      </InquiryBox>
    </PaymentInfoBox>
  );
}

export default PaymentInfo;
