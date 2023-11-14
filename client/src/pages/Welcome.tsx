import tw from 'tailwind-styled-components';
import 'tailwindcss/tailwind.css';
import Page1 from '../components/IntroPage/Page1';
import WelcomeLayout from '../components/Layout/WelcomeLayout';
import Page2 from '../components/IntroPage/Page2';
import Page3 from '../components/IntroPage/Page3';
import Page4 from '../components/IntroPage/Page4';
import Page5 from '../components/IntroPage/Page5';

const Welcome = () => {
  return (
    <WelcomeLayout
      child={
        <WelcomeContainer>
          <Page1 />
          <Page2 />
          <Page3 />
          <Page4 />
          <Page5 />
        </WelcomeContainer>
      }
    />
  );
};

const WelcomeContainer = tw.div`
  pt-60px
  overflow-hidden
`;

export default Welcome;
