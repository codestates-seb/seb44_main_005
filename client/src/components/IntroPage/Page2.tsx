import activity from '../../assets/activity.svg';
import bottom from '../../assets/bottom.svg';
import top from '../../assets/top.svg';

import {
  PageContainer,
  MainText,
  SubText,
} from '../../styles/Welcome/IntroPage/Page2';

function Page2() {
  return (
    <PageContainer id="page2">
      <a
        className="absolute top-5 left-[50%] translate-x-[-50%] z-50"
        href="#page1"
      >
        <img
          src={top}
          className="w-[80px] animate-topbounce cursor-pointer duration-500 ease-in-out hover:w-[100px]"
        />
      </a>
      <MainText
        data-aos="fade-up"
        data-aos-offset="200"
        data-aos-easing="ease-in-out"
        data-aos-duration="1000"
      >
        제주도 수상레저는 모두 엑티온으로
      </MainText>
      <SubText
        data-aos="fade-up"
        data-aos-delay="500"
        data-aos-offset="100"
        data-aos-easing="ease-in-out"
        data-aos-duration="1000"
      >
        무더운 여름 친구들과 함께 짜릿한 추억을 만들어보세요.
      </SubText>
      <img
        data-aos="zoom-in"
        data-aos-delay="1000"
        data-aos-easing="ease-in-out"
        data-aos-duration="1000"
        src={activity}
        className="w-[800px] h-[500px] mb-[20px]"
      />
      <a
        className="absolute bottom-5 left-[50%] translate-x-[-50%] z-50"
        href="#page3"
      >
        <img
          src={bottom}
          className="w-[80px] animate-bounce cursor-pointer duration-500 ease-in-out hover:w-[100px]"
        />
      </a>
    </PageContainer>
  );
}

export default Page2;
