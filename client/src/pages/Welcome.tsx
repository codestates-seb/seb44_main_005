import { useNavigate } from 'react-router-dom';

import logo from '../assets/logo.svg';
import welcome from '../assets/welcome.svg';
import { Button, Blue, Inrto } from '../styles/Welcome/Welcome';

function Welcome() {
  const navigate = useNavigate();

  const handleButton = () => {
    setTimeout(() => {
      navigate('/home');
    }, 300);
  };

  return (
    <div
      className="w-screen h-screen bg-cover"
      style={{
        backgroundImage: `url(${welcome})`,
      }}
    >
      <header className="absolute">
        <img src={logo} className="relative left-5 top-5 w-[220px] h-[60px]" />
      </header>
      <div>
        <div className="absolute text-end">
          <Inrto>
            다양한 <Blue>레저 서비스</Blue>를 <br />
            <Blue>연결</Blue>해주는
            <br /> <Blue>액티온</Blue>입니다.
          </Inrto>
          <Button onClick={handleButton}>시작하기</Button>
        </div>
      </div>
    </div>
  );
}

export default Welcome;
