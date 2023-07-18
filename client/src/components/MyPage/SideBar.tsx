import { Link } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { isLoginState, Role } from '../../store/userInfoAtom';

import {
  SideContainer,
  SideSpace,
  SideTitle,
  SideList,
} from '../../styles/MyPage/SideBar';

function SideBar() {
  const isLogin = useRecoilValue(isLoginState);
  const userRole = useRecoilValue(Role);

  const handleLinkClick = (e) => {
    if (userRole !== 'PARTNER') {
      e.preventDefault();
      alert('접근 권한이 없습니다.');
    }
  };

  return (
    <SideContainer>
      <SideSpace>
        <SideTitle>마이페이지</SideTitle>
        <SideList>
          <Link to="/my">내 정보 관리</Link>
          <Link to="/my/wish">위시리스트</Link>
          <Link to="/my/order">예약 내역 조회</Link>
        </SideList>
      </SideSpace>
      <SideSpace>
        <SideTitle>파트너쉽</SideTitle>
        <SideList>
          {isLogin && (
            <Link to="/my/stores" onClick={handleLinkClick}>판매 서비스 관리</Link>
          )}
        </SideList>
      </SideSpace>
    </SideContainer>
  );
}

export default SideBar;
