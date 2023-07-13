import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from "recoil";

import DetailContent from "../components/CategoryDetail/DetailContent";
import LocationInfo from "../components/CategoryDetail/LocationInfo";
import PaymentInfo from "../components/CategoryDetail/PaymentInfo";
import ReservationInfo from "../components/CategoryDetail/ReservationInfo";
import Review from "../components/CategoryDetail/Review";
import TicketSelect from "../components/CategoryDetail/TicketSelect";
import { CategoryDetailState, ReserFormState } from '../store/categoryDetailAtom';

function CategoryDetail() {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const location = useLocation();
  const [data, setData] = useRecoilState(CategoryDetailState);
  const form = useRecoilValue(ReserFormState);

  const CategoryDetailFetch = async () => {
    try {
      const storeId = location.pathname.substring(10);
      const res = await fetch(`${API_URL}/stores/${storeId}`);
      const json = await res.json();
      delete json.items;
      setData(json);
    }
    catch(error) {
      console.log(error);
    }
  }

  const dateFetch = async () => {
    const storeId = location.pathname.substring(10);
    try {
      const date = form.reservationDate.split('-').join('');
      const res = await fetch(`${API_URL}/items/${storeId}?date=${date}`);
      const json = await res.json();
      setData((prev) => ({...prev, items: json}));
    }
    catch(error) {
      console.log(error);
    }
  }

  useEffect(() => {
    CategoryDetailFetch();
  }, [])

  useEffect(() => {
    dateFetch();
  }, [form.reservationDate])

  return (
    <section className="flex justify-center">
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
