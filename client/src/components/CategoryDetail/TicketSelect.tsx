import { useEffect } from 'react';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';

import TicketCard from "./TicketCard";
import {
  CategoryDetailState,
  ReserDateState,
  ReserFormState,
  itemsState,
  totalPrice
} from '../../store/categoryDetailAtom';
import { DateInput,
  DateSelectBox,
  SelectBox,
  TicketBox
} from '../../styles/CategoryDetail/TicketSelect';

function TicketSelect() {
  const [date, setDate] = useRecoilState(ReserDateState);
  const [total, setTotal] = useRecoilState(totalPrice);
  const data = useRecoilValue(CategoryDetailState);
  const setItems = useSetRecoilState(itemsState);
  const setForm = useSetRecoilState(ReserFormState);

  const dateChangeHandler = (e) => {
    const today = new Date().toLocaleDateString();
    const selectDate = new Date(e.target.value).toLocaleDateString();
    if (selectDate < today) {
      return alert('지난 날짜는 선택할 수 없습니다.');
    }
    setDate(e.target.value);
    setForm((prev) => ({...prev, reservationDate: e.target.value}));
  }

  useEffect(() => {
    setForm((prev) => ({...prev, reservationDate: date}));
    if (data.items) {
      setForm((prev) => ({...prev, reservationItems: []}));
      setItems([]);
      setTotal(0);
      data.items.forEach((item) => {
        setItems((prev) => [...prev, {
          itemId: item.itemId,
          ticketCount: 0
        }])
      })
    }
  }, [data.items])

  return (
    <section className="w-[600px] mb-10">
      <div className="text-2xl font-semibold mb-3">티켓선택</div>
      <SelectBox>
        <div className="mb-7">
          <div className="font-medium mb-3">1. 원하시는 날짜를 선택해주세요.</div>
          <DateSelectBox>
            <DateInput onChange={dateChangeHandler} type="date" value={date} />
          </DateSelectBox>
        </div>
        <div>
          <div className="font-medium mb-3">2. 원하시는 상품을 선택해주세요.</div>
          <TicketBox>
            <div className="border-b-[1px] border-[#4771B7] pb-3">
              {data.items && data.items.map((item, idx) => {
                return <TicketCard item={item} itemIdx={idx} key={idx} />
              })}
            </div>
            <div className="text-right pt-3 mb-5">
              <span className="mr-5 font-bold">총 상품 금액</span>
              <span className="font-bold text-[#4771B7] text-xl">{total.toLocaleString('ko-KR')} 원</span>
            </div>
          </TicketBox>
        </div>
      </SelectBox>
    </section>
  );
}

export default TicketSelect;
