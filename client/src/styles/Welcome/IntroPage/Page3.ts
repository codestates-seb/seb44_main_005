import tw from 'tailwind-styled-components';

export const Wrapper = tw.div`
  relative
  bg-[#4771B7] 
  w-[100vw]
  h-[100vh] 
  flex 
  flex-col
  items-center justify-center
`;
export const PageContainer = tw.div`
  mb-10
  bg-[#4771B7] 
  w-full 
  h-full
  flex 
  items-center 
  justify-around
`;

export const TextContainer = tw.div`
  flex 
  flex-col 
  items-center
`;

export const MainText = tw.div`
  text-[#FFFFFF] 
  text-[48px] 
  font-semibold 
  text-center 
  mb-[30px]
`;

export const SubText = tw.p`
  text-[#FFFFFF] 
  text-[32px] 
  mb-[30px]
`;

export const HomeBtn = tw.button`
  w-[330px] 
  h-[65px] 
  text-[25px] 
  font-medium 
  border 
  bg-white 
  text-center 
  rounded-[30px] 
  shadow-md 
  shadow-[#cdd2d8]
  cursor-pointer
`;
