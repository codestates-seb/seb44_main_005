import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import DetailContent from '../components/CategoryDetail/DetailContent';
import LocationInfo from '../components/CategoryDetail/LocationInfo';
import PaymentInfo from '../components/CategoryDetail/PaymentInfo';
import ReservationInfo from '../components/CategoryDetail/ReservationInfo';
import Review from '../components/CategoryDetail/Review';
import TicketSelect from '../components/CategoryDetail/TicketSelect';
import {
  CategoryDetailState,
  ReserDateState,
  ReserFormState,
} from '../store/categoryDetailAtom';
import { reserFormType } from '../intefaces/CategoryDetail';

function CategoryDetail() {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const [data, setData] = useRecoilState(CategoryDetailState);
  const [form, setForm] = useRecoilState(ReserFormState);
  const date = useRecoilValue(ReserDateState);
  const location = useLocation();

  const categoryDetailFetch = async () => {
    try {
      const storeId = location.pathname.substring(10);
      const res = await fetch(`${API_URL}/stores/${storeId}`, {
        method: 'GET',
        headers: { Authorization: sessionStorage.getItem('Authorization') },
      });
      const json = await res.json();
      delete json.items;
      setData(json);
    } catch (error) {
      console.error(error);
    }
  };

  const itemsFetch = async () => {
    const storeId = location.pathname.substring(10);
    try {
      const dateValue = date.split('-').join('');
      const res = await fetch(`${API_URL}/items/${storeId}?date=${dateValue}`, {
        method: 'GET',
        headers: { Authorization: sessionStorage.getItem('Authorization') },
      });
      const json = await res.json();
      setData((prev) => ({ ...prev, items: json }));
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    window.scrollTo(0, 0);
    setForm({
      reservationName: '',
      reservationPhone: '',
      reservationEmail: '',
      reservationDate: '',
      reservationItems: [],
      totalPrice: 0
    } as reserFormType);
    categoryDetailFetch();
    itemsFetch();
  }, []);

  useEffect(() => {
    itemsFetch();
  }, [form.reservationDate]);

  return (
    <section className="flex justify-center my-[100px]">
      {data &&
        <section className="flex flex-col items-center">
          <DetailContent />
          <ReservationInfo />
          <TicketSelect />
          <LocationInfo />
          <Review />
        </section>
      }
      <PaymentInfo />
    </section>
  );
}

export default CategoryDetail;
