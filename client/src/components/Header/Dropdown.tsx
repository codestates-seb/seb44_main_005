import { Link, useNavigate } from 'react-router-dom';

import {
  StyledContainer,
  Menu,
  DropDownContainer,
} from '../../styles/Header/Dropdown';
import { useSetRecoilState } from 'recoil';
import { isLoginState } from '../../store/userInfoAtom';

function Dropdown() {
  const setIsLoginState = useSetRecoilState(isLoginState);
  const navigate = useNavigate();

  const handleLogout = () => {
    setIsLoginState(false);
    sessionStorage.removeItem('Authorization');
    sessionStorage.removeItem('memberId');
    sessionStorage.removeItem('access_token');
    navigate('/home');
  };

  return (
    <DropDownContainer>
      <StyledContainer>
        <Menu className="border-b-[1px] border-[#4771B7]">
          <Link to="/my">마이페이지</Link>
        </Menu>
        <Menu>
          <div onClick={handleLogout}>로그아웃</div>
        </Menu>
      </StyledContainer>
    </DropDownContainer>
  );
}

export default Dropdown;
