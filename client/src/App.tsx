import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import MainRouter from './router/MainRouter';
import CategoryRouter from './router/CategoryRouter';
import MyRouter from './router/MyRouter';
import { Role, isLoginState, isProfile } from './store/userInfoAtom';

function App() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const setIsLoginState = useSetRecoilState(isLoginState);
  const setIsProfile = useSetRecoilState(isProfile);
  const setIsRole = useSetRecoilState(Role);
  const url = import.meta.env.VITE_APP_API_URL;

  const handleAccessToken = async () => {
    const accessToken = searchParams.get('access_token');
    if (accessToken) {
      try {
        const res = await fetch(`${url}/google/login`, {
          headers: { Authorization: `Bearer ${accessToken}` },
        });
        const result1 = await res.json();
        if (res.status !== 200) throw res;

        //헤더에서 멤버아이디와 닉네임을 받아옴
        const name = result1.nickname;
        const profile = result1.profileImage;
        const role = result1.role;
        setIsProfile(profile);
        setIsRole(role);
        // 로컬 스토리지에 memberId,토큰 저장
        sessionStorage.setItem('Authorization', `Bearer ${accessToken}`);
        setIsLoginState(true);

        // 헤더에서 데이터를 받았으면 리다이렉트
        if (name) {
          alert(`${name}님 반갑습니다 !`);
          navigate('/home');
        }
      } catch (error) {
        console.error('로그인 요청 중 오류가 발생했습니다', error);
      }
      navigate('/home');
    }
  };

  useEffect(() => {
    handleAccessToken();
  }, []);

  return (
    <>
      <MainRouter />
      <CategoryRouter />
      <MyRouter />
    </>
  );
}

export default App;
