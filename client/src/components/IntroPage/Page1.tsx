import { useNavigate } from 'react-router-dom';

import logo from '../../assets/logo.svg';
import bg3 from '../../assets/bg3.svg';
import bottom from '../../assets/bottom.svg';
import { Button, Blue, Inrto } from '../../styles/Welcome/Welcome';

function page1() {
  // eslint-disable-next-line react-hooks/rules-of-hooks
  const navigate = useNavigate();

  const handleButton = () => {
    setTimeout(() => {
      navigate('/home');
    }, 300);
  };

  return (
    <div className="w-full min-h-[100vh] overflow-x-hidden">
      {/* <div className="absolute w-[880px] h-[100vh] bg-cover right-0 top-[-10px]"> */}
      <img
        src={bg3}
        className="absolute bg-cover w-[800px] rounded-l-full top-0 right-0"
      />
      {/* </div> */}
      <img src={logo} className="z-0 absolute w-[200px] left-7 top-5" />
      <div className="intro-text relative top-5 max-w-full">
        <Inrto>
          다양한 <Blue>레저 서비스</Blue>를 <br />
          <Blue>연결</Blue>해주는
          <br /> <Blue>액티온</Blue>입니다.
        </Inrto>
        <Button onClick={handleButton}>시작하기</Button>
      </div>
      <div className="flex flex-col items-center relative justify-center top-[490px]">
        <span className="font-semibold text-[25px] ">엑티온 알아보기</span>
        <img src={bottom} className="w-[100px] animate-bounce" />
      </div>
    </div>
  );
}

export default page1;
