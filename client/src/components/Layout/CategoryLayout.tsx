import { Outlet, useLocation } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { searchKeyword } from '../../store/searchbarAtom';

import Categorybar from '../Categorybar/Categorybar';

function CategoryLayout() {
  const location = useLocation();
  const keyword = useRecoilValue(searchKeyword);
  const showCategoryBar =
    keyword !== null && !location.pathname.includes('/search?');

  return (
    <>
      {showCategoryBar && <Categorybar />}
      <Outlet />
    </>
  );
}

export default CategoryLayout;
