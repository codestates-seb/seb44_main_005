import { useState } from 'react';
import { useSetRecoilState } from 'recoil';
import { Role, isLoginState, isProfile } from '../store/userInfoAtom';

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
  // const CLIENT_ID = import.meta.env.VITE_APP_CLIENT_ID;
  // const GOOGLE_REDIRECT_URI = import.meta.env.VITE_APP_REDIRECT_URI;

  const [email, setEmail] = useState('');
  const [password, setPassWord] = useState('');
  // const [accessToken, setAccessToken] = useState('');

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

  //구글로그인
  // const getAccessToken = async (authorizationCode) => {
  //   // console.log('3');
  //   // await fetch(`${url}/oauth2/authorization/google`, {
  //   //   method: 'POST',
  //   //   body: JSON.stringify({
  //   //     accesstoken: authorizationCode,
  //   //   }),
  //   // });
  //   // setIsLoginState(true);
  //   const parsedHash = new URLSearchParams(window.location.hash.substring(1));
  //   const accessToken = parsedHash.get('access_token');

  //   // await url.post('oauth/google', { accessToken });
  //   await fetch(`${url}/oauth2/authorization/google/success`, {
  //     method: 'POST',
  //     body: JSON.stringify({
  //       accesstoken: accessToken,
  //     }),
  //   });
  //   // setIsLoginState(true);
  //   setIsLoginState(true);
  //   navigate('/home');
  // };
  // useEffect(() => {
  //   // Authorization Server로부터 클라이언트로 리디렉션된 경우, Authorization Code가 함께 전달됩니다.
  //   // ex) http://localhost:3000/mypage?code=5e52fb85d6a1ed46a51f
  //   // 4. [Github Auth 서버 ->클라이언트] Redirect + Authorization code 확인
  //   console.log('3');
  //   const url = new URL(window.location.href);
  //   const authorizationCode = url.searchParams.get('code');
  //   if (authorizationCode) {
  //     getAccessToken(authorizationCode);
  //   }
  // }, []);
  // const getAccessToken = async (accessToken) => {
  //   console.log('1');
  //   try {
  //     await fetch(`${url}/oauth2/authorization/google`, {
  //       method: 'POST',
  //       headers: {
  //         'Content-Type': 'application/json',
  //       },
  //       body: JSON.stringify({
  //         accessToken: accessToken,
  //       }),
  //     });
  //     // const { accessToken } = result.data;
  //     setIsLoginState(true);
  //     setAccessToken(accessToken);
  //     navigate('/home');
  //   } catch (err) {
  //     alert(err);
  //   }
  // };

  // const handleAccessToken = async () => {
  //   const parsedHash = new URLSearchParams(window.location.hash.substring(1));
  //   console.log('2');
  //   const accessToken = parsedHash.get('access_token');
  //   if (accessToken) {
  //     await getAccessToken(accessToken);
  //   }
  // };

  const handleGoogleLogin = async (e) => {
    e.preventDefault();
    window.location.href = `${url}/oauth2/authorization/google`;
    // 'https://accounts.google.com/o/oauth2/auth?' +
    // `client_id=${CLIENT_ID}&` +
    // `redirect_uri=${GOOGLE_REDIRECT_URI}&` +
    // 'response_type=token&' +
    // 'scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile';
  };

  // useEffect(() => {
  //   handleAccessToken();
  // }, []);

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
      const profile = result1.profileImage;
      const role = result1.role;
      setIsProfile(profile);
      setIsRole(role);
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
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      handleLogin(e);
    }
  };

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
        <Button bgColor="#4771B7" color="#FFFFFF" clickHandler={handleLogin}>
          <span className="font-medium">로그인</span>
        </Button>
      </LoginContainer>
    </StyleContainer>
  );
}

export default Login;
