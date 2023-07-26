import { useNavigate } from 'react-router-dom';
import page3 from '../../assets/page3.svg';
import w_top from '../../assets/w_top.svg';
import w_bottom from '../../assets/w_bottom.svg';

import {
  Wrapper,
  PageContainer,
  TextContainer,
  MainText,
  SubText,
  HomeBtn,
} from '../../styles/Welcome/IntroPage/Page3';
1867;
function Page3() {
  const navigate = useNavigate();

  return (
    <Wrapper id="page3">
      <a className="absolute top-5 left-[50%] translate-x-[-50%] z-50" href="#page2">
        <img
          src={w_top}
          className="w-[80px] animate-topbounce cursor-pointer duration-500 ease-in-out hover:w-[100px]"
        />
      </a>
      <PageContainer>
        <img
          src={page3}
          data-aos="fade-right"
          data-aos-easing="ease-in-out"
          data-aos-duration="1000"
          className="w-[600px] h-[700px] mt-10"
        />
        <TextContainer>
          <MainText
            data-aos="fade-down"
            data-aos-delay="500"
            data-aos-easing="ease-in-out"
            data-aos-duration="1000"
          >
            제주도의 모든 레저를
            <br /> 지도로 편리하게 확인해 보세요 !
          </MainText>
          <SubText
            data-aos="flip-right"
            data-aos-delay="500"
            data-aos-easing="ease-in-out"
            data-aos-duration="1000"
          >
            추천상품도 놓치지 말고 확인해 보세요 .
          </SubText>
          <HomeBtn
            data-aos="flip-right"
            data-aos-delay="500"
            data-aos-easing="ease-in-out"
            data-aos-duration="1000"
            onClick={() => navigate('/home')}
          >
            홈으로 이동하기
          </HomeBtn>
        </TextContainer>
      </PageContainer>
      <a className="absolute bottom-5 left-[50%] translate-x-[-50%] z-50" href="#page4">
        <img
          src={w_bottom}
          className="w-[80px] mb-[15px] animate-bounce cursor-pointer duration-500 ease-in-out hover:w-[100px]"
        />
      </a>
    </Wrapper>
  );
}

export default Page3;
