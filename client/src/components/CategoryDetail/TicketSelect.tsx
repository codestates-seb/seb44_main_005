import tw from "tailwind-styled-components";

import TicketCard from "./TicketCard";

function TicketSelect({ data }) {
  return (
    <section className="w-[600px] mb-10">
      <div className="text-2xl font-semibold mb-3">티켓선택</div>
      <SelectBox>
        <div className="mb-7">
          <div className="font-medium mb-3">1. 원하시는 날짜를 선택해주세요.</div>
          <DateSelectBox>
            <DateInput type="date" />
          </DateSelectBox>
        </div>
        <div>
          <div className="font-medium mb-3">2. 원하시는 상품을 선택해주세요.</div>
          <TicketBox>
            <div className="border-b-[1px] border-[#4771B7] pb-3">
              {data.items.map((item, idx) => {
                return <TicketCard item={item} key={idx} />
              })}
            </div>
            <div className="text-right pt-3 mb-5">
              <span className="mr-5 font-bold">총 상품 금액</span>
              <span className="font-bold text-[#4771B7] text-xl">200,000원</span>
            </div>
          </TicketBox>
        </div>
      </SelectBox>
    </section>
  );
}

export default TicketSelect;

const SelectBox = tw.div`
  border-[1px] border-[#4771B7] rounded-[5px]
  px-10 py-5
`;

const DateSelectBox = tw.div`
  inline-block
  p-5
  border-[1px] border-[#4771B7] rounded-[5px]
`;

const DateInput = tw.input`
  bg-[#4771B7]
  rounded-[5px]
  py-2 px-5
  text-center text-white
`;

const TicketBox = tw.div`
  border-[1px] border-[#4771B7] rounded-[5px]
  p-5
  bg-[#EDF1F8]
`;