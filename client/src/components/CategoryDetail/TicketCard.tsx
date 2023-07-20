import { useState, useEffect } from 'react';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import tw from 'tailwind-styled-components';
import { AiOutlinePlusCircle, AiOutlineMinusCircle } from 'react-icons/ai';

import { ReserFormState, itemsState, totalPrice } from '../../store/categoryDetailAtom';

function TicketCard({ item, itemIdx }) {
  const [items, setItems] = useRecoilState(itemsState);
  const [update, setUpdate] = useState({...items[itemIdx]});
  const setForm = useSetRecoilState(ReserFormState);
  const setTotal = useSetRecoilState(totalPrice);
  const form = useRecoilValue(ReserFormState);

  const countClickHandler = (keyword: string) => {
    const result = [...items];
    const updateItem = {...items[itemIdx]};
    if (keyword === "plus" && updateItem.ticketCount < item.remainingTicket) {
      updateItem.ticketCount += 1;
      setTotal((prev) => prev + item.price);
    }
    else if (keyword === "minus" && updateItem.ticketCount > 0) {
      updateItem.ticketCount -= 1;
      setTotal((prev) => prev - item.price);
    }
    result[itemIdx] = updateItem;
    setUpdate(updateItem)
    setItems(result);
    setForm((prev) => ({...prev, reservationItems: result}));
  }

  useEffect(() => {
    setUpdate({...items[itemIdx], ticketCount: 0})
  }, [form.reservationDate])

  return (
    <TicketCardSection>
      <div className="flex justify-between items-center mb-3">
        <div className="font-semibold text-lg">{item.itemName}</div>
        <div className="flex items-center">
          <AiOutlineMinusCircle
            className="cursor-pointer"
            onClick={() => countClickHandler('minus')}
            size="25"
            color="#4771B7"
          />
          <div className="mx-3">{update.ticketCount ? update.ticketCount : 0}</div>
          <AiOutlinePlusCircle
            className="cursor-pointer"
            onClick={() => countClickHandler('plus')}
            size="25"
            color="#4771B7"
          />
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