import { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { GiCarWheel } from 'react-icons/gi';

import AfterPayment from '../components/PaymentSuccess/AfterPayment';

function PaymentSuccess() {
  // /reservations/verify
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const [status, _] = useState('loading');
  const [searchParams] = useSearchParams();
  const reservationId = searchParams.get('reservationId');
  const orderId = searchParams.get('orderId');
  const accessToken = sessionStorage.getItem('Authorization');

  const verifyFetch = async () => {
    await fetch(`${API_URL}/reservations/verify`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': accessToken
      },
      body: JSON.stringify({reservationId, orderId})
    })
  }

  useEffect(() => {
    verifyFetch();
  }, [])

  switch(status) {
    case 'loading':
      return (
        <div className="h-[70vh] py-[250px] text-center text-2xl font-bold">
          <GiCarWheel className="mx-auto wheel" size="100" color="#4771B7" />
          <div className="mt-5 mb-2">결제 중입니다....</div>
          <div>화면을 벗어나지 마세요.</div>
        </div>
      );
    case 'success':
      return <AfterPayment isSuccess={true} />
    case 'fail' :
      return <AfterPayment isSuccess={false} />
  }
}

export default PaymentSuccess;