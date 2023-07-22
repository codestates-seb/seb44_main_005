import { Route, Routes, Navigate, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
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

const requireLogin = (component) => {
  const isLoggedIn = useRecoilValue(isLoginState);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoggedIn) {
      setIsModalOpen(true);
      setTimeout(() => {
        setIsModalOpen(false);
        navigate('/login');
      }, 2500);
    }
  }, [isLoggedIn]);

  if (!isLoggedIn) {
    return (
      <>
        <div
          className={`fixed top-0 left-0 w-full h-full flex pt-[131.2px] justify-center ${
            isModalOpen ? 'flex' : 'hidden'
          } bg-opacity-20 backdrop-blur text-white text-2xl z-50`}
        >
          <div className="h-[80px] w-[312px] flex justify-center items-center rounded-md text-[18px] bg-[#EDF1F8] text-[#4771B7] font-semibold">
            접근권한이 없습니다.
          </div>
        </div>
        {component}
      </>
    );
  }

  return component;
};

function MyRouter() {
  return (
    <Routes>
      <Route element={<HeaderLayout />}>
        <Route element={<FooterLayout />}>
          <Route element={<SideBarLayout />}>
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
