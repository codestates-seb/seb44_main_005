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
    setTimeout(() => {
      navigate('/home');
    }, 300);
  };

  return (
    <PageContainer>
      <img
        src={bg3}
        className="absolute bg-cover h-[100vh] rounded-l-full top-0 right-0"
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
        <img src={bottom} className="w-[100px] animate-bounce" />
      </ClickContainer>
    </PageContainer>
  );
}

export default page1;
