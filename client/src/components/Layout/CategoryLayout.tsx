import { Outlet } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { search } from '../../store/searchbarAtom';

import Categorybar from '../Categorybar/Categorybar';

function CategoryLayout() {
  const isSearch = useRecoilValue(search);
  return (
    <>
      {!isSearch && <Categorybar />}
      <Outlet />
    </>
  );
}

export default CategoryLayout;
