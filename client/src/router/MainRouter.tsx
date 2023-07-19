import { Routes, Route } from 'react-router-dom';
import Welcome from '../pages/Welcome';
import Home from '../pages/Home';
import Login from '../pages/Login';
import Register from '../pages/Register';
import Partner from '../pages/Partner';
import CategoryLayout from '../components/Layout/CategoryLayout';
import FooterLayout from '../components/Layout/FooterLayout';
import HeaderLayout from '../components/Layout/HeaderLayout';
import PaymentSuccess from '../pages/PaymentSuccess';

function MainRouter() {
  return (
    <Routes>
      <Route path="/" element={<Welcome />} />
      <Route element={<HeaderLayout />}>
        <Route element={<FooterLayout />}>
          <Route path="/partner" element={<Partner />} />
          <Route path="/store/payment/success" element={<PaymentSuccess />} />
          <Route element={<CategoryLayout />}>
            <Route path="/home" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
          </Route>
        </Route>
      </Route>
    </Routes>
  );
}

export default MainRouter;
