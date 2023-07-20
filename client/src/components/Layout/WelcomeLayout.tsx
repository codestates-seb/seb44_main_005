import Footer from '../Footer/Footer';

// eslint-disable-next-line react/prop-types
const WelcomeLayout = ({ child }) => {
  return (
    <div className="relative w-full">
      <div className="layout middle">{child}</div>
      <div className="layout bottom">
        <Footer />
      </div>
    </div>
  );
};

export default WelcomeLayout;
