import { useEffect } from 'react';
import MainRouter from './router/MainRouter';
import CategoryRouter from './router/CategoryRouter';
import MyRouter from './router/MyRouter';
import { useNavigate } from 'react-router-dom';

function App() {
  const navigate = useNavigate();

  useEffect(() => {
    const url = new URL(window.location.href);
    const accessToken = url.searchParams.get('access_token');
    if (accessToken) {
      console.log(accessToken);
      navigate('/home');
    }
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
