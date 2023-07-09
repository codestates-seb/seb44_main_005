import tw from "tailwind-styled-components";

function PaymentInfo({ data }) {
  return (
    <PaymentInfoBox>
      <div>
        <div className="m-5 pb-3 text-lg border-b-[1px] border-black">결제정보</div>
        <div className="m-7 flex justify-between">
          <div>주문금액</div>
          <div>20,000원</div>
        </div>
        <AmountBox>
          <div>총 결제금액</div>
          <div>20,000원</div>
        </AmountBox>
        <RuleBox>
          <div className="font-semibold">예약취소 규정</div>
          <ul className="text-xs list-disc mt-3 pl-5">
            <li>체험일 4일전 18시이전 100% 환불가능</li>
            <li>체험일 4일전 18시이후~당일 :  환불불가, 날짜 변경 불가</li>
            <li>부분 사용 및 부분 취소는 불가능합니다.</li>
          </ul>
        </RuleBox>
        <PaymentButton type="button">20,000원 결제하기</PaymentButton>
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

const PaymentInfoBox = tw.section`
  sticky top-10
  w-[300px] h-[600px]
  border-[1px] border-[#4771B7] rounded-[5px]
  mt-10 ml-20
  font-medium
`;

const AmountBox = tw.div`
  bg-[#E7EDF6]
  text-[#4771B7] font-semibold
  flex justify-between
  px-7 py-5
`;

const RuleBox = tw.div`
  w-[250px] h-[135px]
  bg-[#E7EDF6]
  rounded-[5px]
  mx-5 my-7 p-3
`;

const PaymentButton = tw.button`
  bg-[#4771B7]
  w-[250px] h-[50px]
  block mx-auto p-2
  text-white
  rounded-[10px]
`;

const WishButton = tw.button`
  w-[250px] h-[50px]
  block
  mx-auto mt-5 mb-7 p-2
  rounded-[10px] border-[1px] border-[#4771B7]
  font-semibold
`;

const InquiryBox = tw.div`
  border-t-[1px] border-[#4771B7]
  flex justify-center
  text-sm
  p-2
`;

const StoreProfile = tw.img`
  w-[38px] h-[38px]
  object-cover
  rounded-full
  mr-3
`;