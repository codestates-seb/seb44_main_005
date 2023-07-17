import { Outlet } from 'react-router-dom';
import Footer from '../Footer/Footer';
import { loading } from '../../store/searchbarAtom';
import { useRecoilValue } from 'recoil';

function FooterLayout() {
  const isSearch = useRecoilValue(loading);
  return (
    <>
      <Outlet />
      {!isSearch && <Footer />}
    </>
  );
}

export default FooterLayout;
