import tw from "tailwind-styled-components";

function ReservationInfo({ data }) {
  return (
    <section className="w-[600px] mb-10">
      <ReservationTitle>예약자 정보</ReservationTitle>
      <div className="flex ml-7 mb-3 items-center">
        <div className="mr-5 text-lg font-medium">예약자</div>
        <div className="relative">
          <ReservationInput type="text" placeholder={data.nickname} />
          <InputRequire>필수</InputRequire>
        </div>
      </div>
      <div className="flex ml-7 mb-3 items-center">
        <div className="mr-5 text-lg font-medium">연락처</div>
        <div className="relative">
          <ReservationInput type="text" placeholder={data.phoneNumber} />
          <InputRequire>필수</InputRequire>
        </div>
      </div>
      <div className="flex ml-7 mb-3 items-center">
        <div className="mr-5 text-lg font-medium">이메일</div>
        <ReservationInput type="text" placeholder={data.email} />
      </div>
    </section>
  );
}

export default ReservationInfo;

const ReservationTitle = tw.div`
  text-2xl font-semibold
  border-b-[1px] border-[#4771B7]
  pb-3 mb-10
`;

const ReservationInput = tw.input`
  border-[1px] border-[#CCCCCC] rounded-[5px]
  w-[240px] h-[38px]
  p-2
`;

const InputRequire = tw.div`
  absolute top-2 right-2
  text-[#FF0000] font-medium
`;