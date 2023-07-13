import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useRecoilState, useSetRecoilState } from 'recoil';

import { isLoginState } from '../../store/userInfoAtom';
import headerlogo from '../../assets/headerlogo.svg';
import profile from '../../assets/profile.svg';
import Searchbar from './Searchbar';

import {
  HaederContainer,
  LogoContainer,
  LoginContainer,
  UnLoginContainer,
  DropdownContainer,
} from '../../styles/Header/Haeder';
import Dropdown from './Dropdown';
import { search, searchKeyword } from '../../store/searchbarAtom';

function Header() {
  //비로그인 상태일때
  //로그인 된 상태일때 -> 1. 파트너 로그인을 한 상태일때 2. 파트너 로그인을 하지 않은 상태일 때
  //UseContainer 안 요소를 다르게 설정해줄 것
  const [isLogin, setIsLogin] = useRecoilState(isLoginState);
  const setIsSearch = useSetRecoilState(search);
  const [isPartner, setIsPartner] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const setKeyword = useSetRecoilState(searchKeyword);

  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/home');
    setIsSearch(false);
    setKeyword('');
  };
  const onClickPartner = () => {
    if (!isLogin) {
      alert(`로그인 상태에서 이용할 수 있는 서비스입니다.`);
      navigate('/login');
    } else {
      navigate('/partner');
    }
  };
  // const handlePartnerClick = () => {
  //   setIsPartner(!isPartner);
  // };
  const handleDropdownClick = () => {
    setIsOpen(!isOpen);
  };
  console.log(setIsLogin);
  console.log(setIsPartner);
  return (
    <HaederContainer>
      <LogoContainer>
        <img
          className="w-[140px] h-[40px] cursor-pointer"
          alt="logo"
          src={headerlogo}
          onClick={handleClick}
        />
        <Searchbar />
      </LogoContainer>
      {!isLogin ? (
        <UnLoginContainer>
          <div className="cursor-pointer mr-[50px]" onClick={onClickPartner}>
            파트너 등록
          </div>
          <Link to="/login" className="mr-[50px]">
            로그인
          </Link>
          <Link
            to="/register"
            className="border border-[#4771B7] text-[#4771B7] px-[30px] py-2"
          >
            회원가입
          </Link>
        </UnLoginContainer>
      ) : (
        <LoginContainer>
          {isPartner ? (
            <>
              <Link to="/partner" className="mt-1">
                업체 등록
              </Link>
              <DropdownContainer>
                <img
                  src={profile}
                  className="w-[28px] ml-[20px]"
                  onClick={handleDropdownClick}
                />
                {isOpen && <Dropdown />}
              </DropdownContainer>
            </>
          ) : (
            <>
              <Link to="/partner" className="mt-1">
                파트너 등록
              </Link>
              <img
                src={profile}
                className="w-[28px] ml-[50px]"
                onClick={handleDropdownClick}
              />
              {isOpen && <Dropdown />}
            </>
          )}
        </LoginContainer>
      )}
    </HaederContainer>
  );
}

export default Header;
