import { Outlet } from 'react-router-dom';
import SideBar from '../MyPage/SideBar';

function SideBarLayout() {
  return (
    <>
      <div className='flex flex-row justify-center pt-20 space-x-10'>
        <SideBar />
        <Outlet />
      </div>
    </>
  );
}

export default SideBarLayout;
