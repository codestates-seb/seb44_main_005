import { Link } from 'react-router-dom';

import { StyledContainer, Menu } from '../../styles/Header/Dropdown';
import { useSetRecoilState } from 'recoil';
import { isLoginState } from '../../store/userInfoAtom';

function Dropdown() {
  const setIsLoginState = useSetRecoilState(isLoginState);

  const handleLogout = () => {
    setIsLoginState(false);
    localStorage.removeItem('Authorization');
    localStorage.removeItem('memberId');
    window.location.href = '/home';
  };
  return (
    <StyledContainer>
      <Menu className="border-b-[1px] border-[#4771B7]">
        <Link to="/mypage">마이페이지</Link>
      </Menu>
      <Menu>
        <div onClick={handleLogout}>로그아웃</div>
      </Menu>
    </StyledContainer>
  );
}

export default Dropdown;
