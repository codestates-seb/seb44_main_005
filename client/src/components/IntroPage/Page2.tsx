import activity from '../../assets/activity.svg';
import bottom from '../../assets/bottom.svg';

import {
  PageContainer,
  MainText,
  SubText,
} from '../../styles/Welcome/IntroPage/Page2';

function Page2() {
  const pageMove = () => {
    window.scrollTo({ top: 1880, left: 0, behavior: 'smooth' });
  };
  return (
    <PageContainer>
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
        className="w-[900px] h-[600px] mb-[20px]"
      />
      <img
        src={bottom}
        className="w-[80px] animate-bounce"
        onClick={pageMove}
      />
    </PageContainer>
  );
}

export default Page2;
