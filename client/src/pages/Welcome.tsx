import tw from 'tailwind-styled-components';
import Page1 from '../components/IntroPage/Page1';
import WelcomeLayout from '../components/Layout/WelcomeLayout';
import Page2 from '../components/IntroPage/Page2';

const Welcome = () => {
  return (
    <WelcomeLayout
      child={
        <WelcomeContainer>
          <Page1 />
          <Page2 />
        </WelcomeContainer>
      }
    />
  );
};

const WelcomeContainer = tw.div`
  pt-60px
`;

export default Welcome;
