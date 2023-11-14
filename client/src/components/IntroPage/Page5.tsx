import { useNavigate } from 'react-router-dom';
import page5 from '../../assets/page5.svg';
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
    <Wrapper id="page5">
      <a className="absolute top-5 left-[50%] translate-x-[-50%] z-50" href="#page4">
        <img
          src={w_top}
          className="w-[80px] mt-[15px] animate-topbounce cursor-pointer duration-500 ease-in-out hover:w-[100px]"
        />
      </a>
      <PageContainer>
        <img
          src={page5}
          data-aos="fade-right"
          data-aos-easing="ease-in-out"
          data-aos-duration="1000"
          className="w-[600px] h-[650px]"
        />
        <TextContainer>
          <MainText
            data-aos="fade-down"
            data-aos-delay="500"
            data-aos-easing="ease-in-out"
            data-aos-duration="1000"
          >
            다양한 결제 방식으로
            <br /> 쉽고 빠르게 결제를 진행해 보세요 !
          </MainText>
          <SubText
            data-aos="flip-right"
            data-aos-delay="500"
            data-aos-easing="ease-in-out"
            data-aos-duration="1000"
          >
            결제 완료 후 예약내역을 확인해 보세요.
          </SubText>
          <HomeBtn
            data-aos="flip-right"
            data-aos-delay="500"
            data-aos-easing="ease-in-out"
            data-aos-duration="1000"
            onClick={() => navigate('/home')}
          >
            엑티온 시작하기
          </HomeBtn>
        </TextContainer>
      </PageContainer>
      <div className="flex flex-col items-center mb-[10px] absolute bottom-5 left-[50%] translate-x-[-50%] z-50">
        <div className="font-medium text-white text-[18px] ml-1">
          다시 감상하려면 누르세요!
        </div>
        <a href="#page1">
          <img
            src={w_bottom}
            className="w-[80px] mt-[5px] animate-bounce cursor-pointer duration-500 ease-in-out hover:w-[100px]"
          />
        </a>
      </div>
    </Wrapper>
  );
}

export default Page3;
