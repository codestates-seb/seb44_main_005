import { Link } from 'react-router-dom';
import {
  SideContainer,
  SideSpace,
  SideTitle,
  SideList
} from '../../styles/MyPage/SideBar';

function SideBar() {
  return (
    <SideContainer>
      <SideSpace>
        <SideTitle>마이페이지</SideTitle>
        <SideList>
            <Link to='/my'>
                내 정보 관리
            </Link>
            <Link to='/my/wish'>
                위시리스트
            </Link>
            <Link to='/my/order'>
                예약 내역 조회
            </Link>
        </SideList>
      </SideSpace>
      <SideSpace>
        <SideTitle>파트너쉽</SideTitle>
        <SideList>
            <Link to='/my/stores'>
                판매 서비스 관리
            </Link>
        </SideList>
      </SideSpace>
    </SideContainer>
  );
}

export default SideBar;
