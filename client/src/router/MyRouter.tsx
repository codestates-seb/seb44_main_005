import { Route, Routes } from 'react-router-dom';
import { motion } from 'framer-motion';

import MyPage from '../pages/MyPage';
import ReservationModify from '../pages/ReservationModify';
import ReservationCheck from '../pages/ReservationCheck';
import WishList from '../pages/WishList';
import StoreCheck from '../pages/StoreCheck';
import StoreAdd from '../pages/StoreAdd';
import HeaderLayout from '../components/Layout/HeaderLayout';
import FooterLayout from '../components/Layout/FooterLayout';
import SideBarLayout from '../components/Layout/SideBarLayout';

function MyRouter() {
  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.5 }}
      animate={{ opacity: 1, scale: 1 }}
      transition={{
        duration: 0.8,
        delay: 0.5,
        ease: [0, 0.71, 0.2, 1.01],
      }}
    >
      <Routes>
        <Route element={<HeaderLayout />}>
          <Route element={<FooterLayout />}>
            <Route element={<SideBarLayout />}>
              <Route path="/my" element={<MyPage />} />
              <Route path="/my/wish" element={<WishList />} />
              <Route path="/my/order" element={<ReservationCheck />} />
              <Route path="/my/stores" element={<StoreCheck />} />
            </Route>
            <Route path="/my/order/edit" element={<ReservationModify />} />
            <Route path="/store/add" element={<StoreAdd />} />
            <Route path="/store/edit" element={<StoreAdd />} />
          </Route>
        </Route>
      </Routes>
    </motion.div>
  );
}

export default MyRouter;
