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
          <a>내 정보 관리</a>
          <a>위시리스트</a>
          <a>예약 내역 조회</a>
        </SideList>
      </SideSpace>
      <SideSpace>
        <SideTitle>파트너쉽</SideTitle>
        <SideList>
          <a>판매 서비스 관리</a>
        </SideList>
      </SideSpace>
    </SideContainer>
  );
}

export default SideBar;
