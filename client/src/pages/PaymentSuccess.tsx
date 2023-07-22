import { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { GiCarWheel } from 'react-icons/gi';

import AfterPayment from '../components/PaymentSuccess/AfterPayment';

function PaymentSuccess() {
  // /reservations/verify
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const [status, setStatus] = useState('loading');
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const reservationKey = searchParams.get('reservationKey');
  const orderId = searchParams.get('orderId');
  const accessToken = sessionStorage.getItem('Authorization');

  const verifyFetch = async () => {
    try {
      const res = await fetch(`${API_URL}/reservation/payments?reservationKey=${reservationKey}&orderId=${orderId}`, {
        method: 'POST',
        headers: {
          'Authorization': accessToken
        }
      })
      if (res.ok) {
        setStatus('success');
      }
      else if (!res.ok) {
        setStatus('fail');
      }
    }
    catch(error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (!reservationKey || !orderId || !accessToken) {
      navigate('/home');
    }
    verifyFetch();
  }, []);

  switch(status) {
    case 'loading':
      return (
        <div className="h-[77vh] py-[250px] text-center text-2xl font-bold">
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
