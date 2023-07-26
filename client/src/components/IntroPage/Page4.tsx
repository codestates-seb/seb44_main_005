import { useNavigate } from 'react-router-dom';
import page4 from '../../assets/page4(2).svg';
import top from '../../assets/top.svg';
import bottom from '../../assets/bottom.svg';

import {
  Wrapper,
  PageContainer,
  TextContainer,
  MainText,
  SubText,
  HomeBtn,
} from '../../styles/Welcome/IntroPage/Page4';

function Page4() {
  const navigate = useNavigate();

  return (
    <Wrapper id="page4">
      <a href="#page3">
        <img
          src={top}
          className="w-[80px] mt-[15px] animate-topbounce cursor-pointer duration-500 ease-in-out hover:w-[100px]"
        />
      </a>
      <PageContainer>
        <TextContainer>
          <MainText>
            즐기고 싶은 레저를 고르고
            <br />
            예약하고 결제까지 진행해 보세요 !
          </MainText>
          <SubText>
            마음에 드는 업체는 위시리스트에 담아두고
            <br />
            한눈에 비교해 보세요 !
          </SubText>
          <HomeBtn onClick={() => navigate('/home')}>업체 보러가기</HomeBtn>
        </TextContainer>
        <img
          src={page4}
          data-aos="fade-left"
          data-aos-delay="500"
          data-aos-easing="ease-in-out"
          data-aos-duration="1000"
          className="w-[700px] h-[650px]"
        />
      </PageContainer>
      <a href="#page5">
        <img
          src={bottom}
          className="w-[80px] mt-[5px] animate-bounce cursor-pointer duration-500 ease-in-out hover:w-[100px]"
        />
      </a>
    </Wrapper>
  );
}

export default Page4;
