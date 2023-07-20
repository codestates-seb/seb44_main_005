import { Route, Routes, Navigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { isLoginState } from '../store/userInfoAtom';

import MyPage from '../pages/MyPage';
import ReservationModify from '../pages/ReservationModify';
import ReservationCheck from '../pages/ReservationCheck';
import WishList from '../pages/WishList';
import StoreCheck from '../pages/StoreCheck';
import StoreAdd from '../pages/StoreAdd';
import HeaderLayout from '../components/Layout/HeaderLayout';
import FooterLayout from '../components/Layout/FooterLayout';
import SideBarLayout from '../components/Layout/SideBarLayout';

// Function to check if user is logged in
const requireLogin = (component) => {
  const isLoggedIn = useRecoilValue(isLoginState);

  if (!isLoggedIn) {
    return <Navigate to="/login" />;
  }

  return component;
};

function MyRouter() {
  return (
    <Routes>
      <Route element={<HeaderLayout />}>
        <Route element={<FooterLayout />}>
          <Route element={<SideBarLayout />}>
            {/* Use the requireLogin function to conditionally render the components */}
            <Route path="/my" element={requireLogin(<MyPage />)} />
            <Route path="/my/wish" element={requireLogin(<WishList />)} />
            <Route path="/my/order" element={requireLogin(<ReservationCheck />)} />
            <Route path="/my/stores" element={requireLogin(<StoreCheck />)} />
          </Route>
          <Route path="/my/order/edit" element={requireLogin(<ReservationModify />)} />
          <Route path="/store/add" element={requireLogin(<StoreAdd />)} />
          <Route path="/store/edit" element={requireLogin(<StoreAdd />)} />
        </Route>
      </Route>
    </Routes>
  );
}

export default MyRouter;
