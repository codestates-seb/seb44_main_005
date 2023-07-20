import { useNavigate } from 'react-router-dom';
import page3 from '../../assets/page3.svg';
import {
  PageContainer,
  TextContainer,
  MainText,
  SubText,
  HomeBtn,
} from '../../styles/Welcome/IntroPage/Page3';

function Page3() {
  const navigate = useNavigate();
  return (
    <PageContainer>
      <img
        src={page3}
        data-aos="fade-right"
        data-aos-easing="ease-in-out"
        data-aos-duration="1000"
        className="w-[700px] h-[650px]"
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
  );
}

export default Page3;
