
import { useState } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { isLoginState } from '../store/userInfoAtom';

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

  const [email, setEmail] = useState('');
  const [password, setPassWord] = useState('');

  //recoil 전역상태
  const isLogin = useRecoilValue(isLoginState);
  const setIsLoginState = useSetRecoilState(isLoginState);

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  };

  const onPwHandler = (event) => {
    setPassWord(event.currentTarget.value);
  };

  //구글로그인
  const handleGoogleLogin = async (e) => {
    e.preventDefault();
    window.location.href =
      await `http://ec2-52-78-205-102.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/google`;
  };

  //일반로그인 -> 공통으로 뺄 것.....axios
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(`${url}/auth/login`, {
        method: 'POST',
        body: JSON.stringify({
          username: email,
          password: password,
        }),
      });
      const result1 = await res.json();
      if (res.status !== 200) throw res;

      //헤더에서 멤버아이디와 닉네임을 받아옴
      const Authorization = res.headers.get('Authorization');
      const name = result1.nickname;
      // 로컬 스토리지에 memberId,토큰 저장
      sessionStorage.setItem('Authorization', Authorization);
      setIsLoginState(true);

      // 헤더에서 데이터를 받았으면 리다이렉트
      if (name) {
        alert(`${name}님 반갑습니다 !`);
        navigate('/home');
      }
    } catch (error) {
      console.error('로그인 요청 중 오류가 발생했습니다', error);
    }
    console.log(isLogin);
  };

  // useEffect(() => {
  //   if (isLogin) {
  //     // 로그인 상태인 경우 새로고침
  //   }
  // }, [isLogin]);

  return (
    <StyleContainer>
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

            />
          </div>
          <div>
            <label className="font-medium">비밀번호</label>
            <input
              type="password"
              value={password}
              onChange={onPwHandler}
              className="border border-[#9A9A9A] text-[13px] h-[30px] w-[200px] ml-3 rounded-md mr-3 p-2"
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
        <Button bgColor="#4771B7" color="#FFFFFF" clickHandler={handleLogin}>
          <span className="font-medium">로그인</span>
        </Button>
      </LoginContainer>
    </StyleContainer>
  );
}

export default Login;
