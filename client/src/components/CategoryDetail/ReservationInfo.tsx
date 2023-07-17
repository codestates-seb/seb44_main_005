import { useRecoilState } from "recoil";
import tw from "tailwind-styled-components";

import { ReserFormState } from "../../store/categoryDetailAtom";

function ReservationInfo() {
  const [form, setForm] = useRecoilState(ReserFormState);

  const reserChangeHandler = (e) => {
    if (e.target.name === "reservationName") {
      setForm({...form, reservationName: e.target.value})
    }
    else if (e.target.name === "reservationPhone") {
      setForm({...form, reservationPhone: e.target.value})
    }
    else if (e.target.name === "reservationEmail") {
      setForm({...form, reservationEmail: e.target.value})
    }
  }

  return (
    <section className="w-[600px] mb-10">
      <ReservationTitle>예약자 정보</ReservationTitle>
      <div className="flex ml-7 mb-3 items-center">
        <div className="mr-5 text-lg font-medium">예약자</div>
        <div className="relative">
          <ReservationInput
            onChange={reserChangeHandler}
            type="text"
            name="reservationName"
          />
          <InputRequire>필수</InputRequire>
        </div>
      </div>
      <div className="flex ml-7 mb-3 items-center">
        <div className="mr-5 text-lg font-medium">연락처</div>
        <div className="relative">
          <ReservationInput
            onChange={reserChangeHandler}
            type="text"
            placeholder="ex) 010-0000-0000"
            name="reservationPhone"
          />
          <InputRequire>필수</InputRequire>
        </div>
      </div>
      <div className="flex ml-7 mb-3 items-center">
        <div className="mr-5 text-lg font-medium">이메일</div>
        <ReservationInput
          onChange={reserChangeHandler}
          type="text"
          name="reservationEmail"
        />
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