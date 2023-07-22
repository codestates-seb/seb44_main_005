import { PiDotsThreeOutlineBold } from 'react-icons/pi';

function LoadingComponent() {
  return (
    <div className="border-[1px] border-[#4771B7] w-[700px] flex flex-col justify-center items-center text-3xl font-semibold space-y-5">
      <PiDotsThreeOutlineBold />
      <p>로딩중입니다.</p>
    </div>
  );
}

export default LoadingComponent;
