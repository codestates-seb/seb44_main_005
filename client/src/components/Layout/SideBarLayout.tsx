import { Outlet } from 'react-router-dom';
import SideBar from '../MyPage/SideBar';
import tw from 'tailwind-styled-components';

function SideBarLayout() {
  return (
    <>
      <Style>
        <SideBar />
        <Outlet />
      </Style>
    </>
  );
}

export default SideBarLayout;

const Style = tw.div`
    flex
    flex-row
    justify-center
    py-12
`;