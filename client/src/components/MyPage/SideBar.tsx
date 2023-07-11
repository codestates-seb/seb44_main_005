import {
  SideContainer,
  SideSpace,
  SideTitle,
  SideList,
  SideLink
} from '../../styles/MyPage/SideBar';

function SideBar({ onTabSelect }) {
  return (
    <SideContainer>
      <SideSpace>
        <SideTitle>마이페이지</SideTitle>
        <SideList>
            <SideLink
                onClick={() => onTabSelect('info')}
            >
                내 정보 관리
            </SideLink>
            <SideLink
                onClick={() => onTabSelect('wish')}
            >
                위시리스트
            </SideLink>
            <SideLink
                onClick={() => onTabSelect('order')}
            >
                예약 내역 조회
            </SideLink>
        </SideList>
      </SideSpace>
      <SideSpace>
        <SideTitle>파트너쉽</SideTitle>
        <SideList>
            <SideLink
                onClick={() => onTabSelect('stores')}
            >
                판매 서비스 관리
            </SideLink>
        </SideList>
      </SideSpace>
    </SideContainer>
  );
}

export default SideBar;
