import { useRecoilState } from "recoil";
import tw from "tailwind-styled-components";

import { ReserFormState } from "../../store/categoryDetailAtom";

function ReservationInfo() {
  const [form, setForm] = useRecoilState(ReserFormState);

  const reserChangeHandler = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    switch(name) {
      case 'reservationName':
        return setForm({...form, reservationName: value})
      case 'reservationPhone':
        const phoneNumberPattern = /(\d{3})(\d{3,4})(\d{4})/;
        let contactVerify = value.replace(/[^0-9]/g, '');
        if (contactVerify.length >= 11) {
          contactVerify = contactVerify.replace(phoneNumberPattern, '$1-$2-$3');
        }
        if (contactVerify.length >= 14) {
          return;
        }
        return setForm({...form, reservationPhone: contactVerify});
      case 'reservationEmail':
        return setForm({...form, reservationEmail: value})
    }
  }

  return (
    <section className="w-[600px] mb-10">
      <ReservationTitle>예약자 정보</ReservationTitle>
      <div className="flex ml-7 mb-3 items-center">
        <div className="mr-5 text-lg font-medium">예약자</div>
        <div className="relative">
          <ReservationInput
            type="text"
            name="reservationName"
            value={form.reservationName}
            onChange={reserChangeHandler}
          />
          <InputRequire>필수</InputRequire>
        </div>
      </div>
      <div className="flex ml-7 mb-3 items-center">
        <div className="mr-5 text-lg font-medium">연락처</div>
        <div className="relative">
          <ReservationInput
            type="text"
            name="reservationPhone"
            value={form.reservationPhone}
            onChange={reserChangeHandler}
            placeholder="'-'를 제외하고 입력해주세요"
          />
          <InputRequire>필수</InputRequire>
        </div>
      </div>
      <div className="flex ml-7 mb-3 items-center">
        <div className="mr-5 text-lg font-medium">이메일</div>
        <ReservationInput
          type="text"
          name="reservationEmail"
          value={form.reservationEmail}
          onChange={reserChangeHandler}
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
  w-[260px] h-[38px]
  p-2
`;

const InputRequire = tw.div`
  absolute top-2 right-2
  text-[#FF0000] font-medium
`;