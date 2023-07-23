import { useNavigate } from 'react-router-dom';

import logo from '../../assets/logo.svg';
import bg3 from '../../assets/bg3.svg';
import bottom from '../../assets/bottom.svg';
import { Button, Blue, Inrto } from '../../styles/Welcome/Welcome';
import {
  PageContainer,
  IntroText,
  ClickContainer,
  HomeBtn,
} from '../../styles/Welcome/IntroPage/Page1';

function page1() {
  // eslint-disable-next-line react-hooks/rules-of-hooks
  const navigate = useNavigate();

  const handleButton = () => {
    navigate('/home');
  };

  return (
    <PageContainer id="page1">
      <img
        src={bg3}
        className="absolute bg-cover w-[40vw] h-[100vh] object-cover rounded-l-full top-0 right-0"
      />
      <img src={logo} className="z-0 absolute w-[200px] left-7 top-5" />
      <IntroText>
        <Inrto>
          다양한 <Blue>레저 서비스</Blue>를 <br />
          <Blue>연결</Blue>해주는
          <br /> <Blue>액티온</Blue>입니다.
        </Inrto>
        <Button onClick={handleButton}>시작하기</Button>
      </IntroText>
      <ClickContainer>
        <HomeBtn>엑티온 알아보기</HomeBtn>
        <a href="#page2">
          <img
            src={bottom}
            className="w-[80px] cursor-pointer animate-bounce duration-500 ease-in-out hover:w-[100px]"
          />
        </a>
      </ClickContainer>
    </PageContainer>
  );
}

export default page1;
