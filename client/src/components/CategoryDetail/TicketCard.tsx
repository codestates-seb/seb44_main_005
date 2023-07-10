import tw from 'tailwind-styled-components';
import { AiOutlinePlusCircle, AiOutlineMinusCircle } from 'react-icons/ai';

function TicketCard({ item }) {
  return (
    <TicketCardSection>
      <div className="flex justify-between items-center mb-3">
        <div className="font-semibold text-lg">{item.itemName}</div>
        <div className="flex items-center">
          <AiOutlineMinusCircle size="25" color="#4771B7" />
          <div className="mx-3">0</div>
          <AiOutlinePlusCircle size="25" color="#4771B7" />
        </div>
      </div>
      <div className="flex justify-between items-center">
        <div className="font-bold text-[#4771B7]">잔여량: {item.remainingTicket}</div>
        <div>
          <span className="mr-3 text-xs font-medium">1인기준</span>
          <span className="text-lg font-bold text-[#4771B7]">{item.price.toLocaleString('ko-KR')}원</span>
        </div>
      </div>
    </TicketCardSection>
  );
}

export default TicketCard;

const TicketCardSection = tw.section`
  shadow-[0px_2px_4px_0px_rgba(0,0,0,0.25)]
  rounded-[5px]
  p-5 mb-5
  bg-white
`;