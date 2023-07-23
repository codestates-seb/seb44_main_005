import { useState } from 'react';
import { useSetRecoilState } from 'recoil';
import { Role, isLoginState, isProfile } from '../store/userInfoAtom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import {
  InputContainer,
  StyleContainer,
  LoginContainer,
  IntroText,
} from '../styles/Login/Login';
import headerlogo from '../assets/headerlogo.svg';
import Button from '../components/Button/Button';
import google from '../assets/google.svg';
import { useNavigate } from 'react-router-dom';

function Login() {
  const navigate = useNavigate();
  const url = import.meta.env.VITE_APP_API_URL;

  const [isClicked, setIClicked] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassWord] = useState('');

  //recoil 전역상태
  const setIsLoginState = useSetRecoilState(isLoginState);
  const setIsProfile = useSetRecoilState(isProfile);
  const setIsRole = useSetRecoilState(Role);

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  };

  const onPwHandler = (event) => {
    setPassWord(event.currentTarget.value);
  };

  //일반로그인 -> 공통으로 뺄 것.....axios
  const handleLogin = async (e) => {
    setIClicked(true);
    if (isClicked) {
      return;
    }
    //true
    e.preventDefault();
    try {
      const res = await fetch(`${url}/auth/login`, {
        method: 'POST',
        headers: { 'Access-Control-Allow-Origin': '*' },
        body: JSON.stringify({
          username: email,
          password: password,
        }),
        credentials: 'include',
      });
      const result1 = await res.json();

      //헤더에서 멤버아이디와 닉네임을 받아옴
      const Authorization = res.headers.get('Authorization');
      const name = result1.nickname;
      const profile = result1.profileImage;
      const role = result1.role;
      if (res.ok) {
        setIsLoginState(true);
        toast(`🌊 로그인 성공 ! ${name}반갑습니다 `);
        setTimeout(() => {
          navigate('/home');
          setIsLoginState(true);
        }, 2000);

        // //헤더에서 멤버아이디와 닉네임을 받아옴

        // 받아온 데이터 전역에 저장
        setIsProfile(profile);
        setIsRole(role);
        // 로컬 스토리지에 memberId,토큰 저장
        sessionStorage.setItem('Authorization', Authorization);
      } else if (res.status === 401) {
        toast('🚨 이메일과 비밀번호를 정확하게 입력해주세요');
        setTimeout(() => {
          setIClicked(false);
        }, 3000);
      }
    } catch (error) {
      console.error(error);
      toast(`🚨 로그인에 실패했습니다!`);
      setTimeout(() => {
        setIClicked(false);
      }, 3000);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      setIClicked(true);
      if (isClicked) {
        return;
      }
      handleLogin(e);
    }
  };

  const handleGoogleLogin = async (e) => {
    e.preventDefault();
    window.location.href = `${url}/oauth2/authorization/google`;
  };

  return (
    <StyleContainer>
      <ToastContainer
        toastClassName={
          'h-[20px] rounded-md text-sm font-medium bg-[#EDF1F8] text-[#4771B7] text-center mt-[70px]'
        }
        position="top-center"
        limit={1}
        closeButton={false}
        autoClose={2000}
        hideProgressBar
      />
      <LoginContainer>
        <img src={headerlogo} className="pl-[30px]" />
        <IntroText>
          레저 스포츠는
          <br /> <span className="text-[#4771B7]">액티온</span>에서
          시작해보세요.
        </IntroText>
        <InputContainer>
          <div>
            <label className="font-medium">이메일</label>
            <input
              type="text"
              value={email}
              onChange={onEmailHandler}
              className="border border-[#9A9A9A] text-[13px] h-[30px] w-[200px] ml-4 rounded-md mb-3 p-2"
              onKeyDown={handleKeyDown}
            />
          </div>
          <div>
            <label className="font-medium">비밀번호</label>
            <input
              type="password"
              value={password}
              onChange={onPwHandler}
              className="border border-[#9A9A9A] text-[13px] h-[30px] w-[200px] ml-3 rounded-md mr-3 p-2"
              onKeyDown={handleKeyDown}
            />
          </div>
        </InputContainer>
        <Button
          bgColor="#FFFFFF"
          color="#000000"
          clickHandler={handleGoogleLogin}
        >
          <div className="flex justify-center items-center">
            <img src={google} className="mr-2" />
            <span className="font-medium">구글로 로그인하기</span>
          </div>
        </Button>
        <Button
          bgColor="#4771B7"
          color="#FFFFFF"
          clickHandler={handleLogin}
          // disabled={isClicked}
        >
          <span className="font-medium">로그인</span>
        </Button>
      </LoginContainer>
    </StyleContainer>
  );
}

export default Login;
