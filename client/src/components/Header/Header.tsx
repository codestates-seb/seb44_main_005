import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import headerlogo from '../../assets/headerlogo.svg';
import profile from '../../assets/profile.svg';
import Searchbar from './Searchbar';
import tw from 'tailwind-styled-components';
import {
  HaederContainer,
  LogoContainer,
  LoginContainer,
  UnLoginContainer,
} from '../../styles/Header/Haeder';
import Dropdown from './Dropdown';

function Header() {
  //비로그인 상태일때
  //로그인 된 상태일때 -> 1. 파트너 로그인을 한 상태일때 2. 파트너 로그인을 하지 않은 상태일 때
  //UseContainer 안 요소를 다르게 설정해줄 것
  const [isLogin, setIsLogin] = useState(false);
  const [isPartner, setIsPartner] = useState(true);
  const [isOpen, setIsOpen] = useState(false);
  const nevigate = useNavigate();

  const handleClick = () => {
    nevigate('/home');
  };
  // const handlePartnerClick = () => {
  //   setIsPartner(!isPartner);
  // };
  const handleDropdownClick = () => {
    setIsOpen(!isOpen);
  };
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
          <Link to="/partner">파트너 등록</Link>
          <Link to="/login">로그인</Link>
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
              <Link to="/partner" className="mt-1.5">
                업체 등록
              </Link>
              <DropdownContainer>
                <img
                  src={profile}
                  className="w-[28px]"
                  onClick={handleDropdownClick}
                />
                {isOpen && <Dropdown />}
              </DropdownContainer>
            </>
          ) : (
            <>
              <Link to="/partner" className="mt-1.5">
                파트너 등록
              </Link>
              <img
                src={profile}
                className="w-[28px]"
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

const DropdownContainer = tw.article`
`;
export default Header;
