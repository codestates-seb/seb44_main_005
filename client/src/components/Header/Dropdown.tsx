import { Link } from 'react-router-dom';

import { StyledContainer, Menu } from '../../styles/Header/Dropdown';

function Dropdown() {
  return (
    <StyledContainer>
      <Menu className="border-b-[1px] border-[#4771B7]">
        <Link to="/mypage">마이페이지</Link>
      </Menu>
      <Menu>
        <Link to="/logout">로그아웃</Link>
      </Menu>
    </StyledContainer>
  );
}

export default Dropdown;
