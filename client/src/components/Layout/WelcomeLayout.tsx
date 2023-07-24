// eslint-disable-next-line react/prop-types
const WelcomeLayout = ({ child }) => {
  return (
    <>
      <div className="layout middle overflow-y-hidden">{child}</div>
    </>
  );
};

export default WelcomeLayout;
