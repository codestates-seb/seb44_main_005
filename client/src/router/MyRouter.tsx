import { Route, Routes } from 'react-router-dom';

import MyPage from '../pages/MyPage';
import ReservationModify from '../pages/ReservationModify';
import ReservationCheck from '../pages/ReservationCheck';
import WishList from '../pages/WishList';
import StoreCheck from '../pages/StoreCheck';
import StoreAdd from '../pages/StoreAdd';
import StoreModify from '../pages/StoreModify';
import HeaderLayout from '../components/Layout/HeaderLayout';
import FooterLayout from '../components/Layout/FooterLayout';

function MyRouter() {
  return (
    <Routes>
      <Route element={<HeaderLayout />}>
        <Route element={<FooterLayout />}>
          <Route path="/my" element={<MyPage />} />
          <Route path="/my/wish" element={<WishList />} />
          <Route path="/my/order" element={<ReservationCheck />} />
          <Route path="/my/order/:id/edit" element={<ReservationModify />} />
          <Route path="/my/stores" element={<StoreCheck />} />
          <Route path="/store/add" element={<StoreAdd />} />
          <Route path="/store/edit" element={<StoreModify />} />
        </Route>
      </Route>
    </Routes>
  );
}

export default MyRouter;
