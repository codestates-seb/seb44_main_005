import tw from 'tailwind-styled-components';
import Page1 from '../components/IntroPage/Page1';
import WelcomeLayout from '../components/Layout/WelcomeLayout';
import Page2 from '../components/IntroPage/Page2';
import Page3 from '../components/IntroPage/Page3';
import Page4 from '../components/IntroPage/Page4';

const Welcome = () => {
  return (
    <WelcomeLayout
      child={
        <WelcomeContainer>
          <Page1 />
          <Page2 />
          <Page3 />
          <Page4 />
        </WelcomeContainer>
      }
    />
  );
};

const WelcomeContainer = tw.div`
  pt-60px
`;

export default Welcome;
