import { Link } from 'react-router-dom';

import {
  StyledContainer,
  Menu,
  DropDownContainer,
} from '../../styles/Header/Dropdown';
import { useSetRecoilState } from 'recoil';
import { isLoginState } from '../../store/userInfoAtom';
import { open } from '../../store/dropdownAtom';

function Dropdown() {
  const setIsLoginState = useSetRecoilState(isLoginState);
  // const url = import.meta.env.VITE_APP_API_URL;
  const setIsOpen = useSetRecoilState(open);

  const handleLogout = async () => {
    if (confirm('정말로 로그아웃 하시겠습니까 ?') == true) {
      //true는 확인버튼을 눌렀을 때 코드 작성
      await fetch(`/logout`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: sessionStorage.getItem('Authorization'),
          'Access-Control-Allow-Origin': '*',
        },
        credentials: 'include',
      });
      setIsOpen(false);
      setIsLoginState(false);
      localStorage.removeItem('recoil-persist');
      sessionStorage.removeItem('Authorization');
      sessionStorage.removeItem('memberId');
      sessionStorage.removeItem('access_token');
      window.location.href = '/home';
    }
  };

  return (
    <DropDownContainer>
      <StyledContainer>
        <Menu className="border-b-[1px] border-[#4771B7]">
          <Link to="/my" onClick={() => setIsOpen(false)}>
            마이페이지
          </Link>
        </Menu>
        <Menu>
          <div onClick={handleLogout}>로그아웃</div>
        </Menu>
      </StyledContainer>
    </DropDownContainer>
  );
}

export default Dropdown;
