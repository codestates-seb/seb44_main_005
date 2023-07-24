import { Outlet } from 'react-router-dom';
import Header from '../Header/Header';

function HeaderLayout() {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
}

export default HeaderLayout;
