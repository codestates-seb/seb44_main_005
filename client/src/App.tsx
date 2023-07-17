import { useEffect } from 'react';
import MainRouter from './router/MainRouter';
import CategoryRouter from './router/CategoryRouter';
import MyRouter from './router/MyRouter';
import { useNavigate } from 'react-router-dom';
// import { useSetRecoilState } from 'recoil';
// import { isLoginState } from './store/userInfoAtom';

function App() {
  const navigate = useNavigate();
  // const url = import.meta.env.VITE_APP_API_URL;

  // const setIsLoginState = useSetRecoilState(isLoginState);

  // const getAccessToken = async (accessToken) => {
  //   console.log('1');
  //   try {
  //     const parsedHash = new URLSearchParams(window.location.hash.substring(1));
  //     console.log('2');
  //     const accessToken = parsedHash.get('access_token');
  //     sessionStorage.setItem('access_token', accessToken);

  //     navigate('/home');
  //     // await fetch(`${url}/oauth2/authorization/google`, {
  //     //   method: 'POST',
  //     //   headers: {
  //     //     'Content-Type': 'application/json',
  //     //     // eslint-disable-next-line prettier/prettier
  //     //     Authorization: "Bearer " + accessToken
  //     //   },
  //     // });
  //     // const { accessToken } = result.data;

  //     setIsLoginState(true);
  //     // setAccessToken(accessToken);
  //   } catch (err) {
  //     alert(err);
  //   }
  // };

  const handleAccessToken = async () => {
    const parsedHash = new URLSearchParams(window.location.href);
    // console.log('2');
    const accessToken = parsedHash.get('access_token');
    // console.log(accessToken);
    if (accessToken) {
      navigate('/home');
      // await getAccessToken(accessToken);
      // console.log(accessToken);
      // navigate('/home');
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
