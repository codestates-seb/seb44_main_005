import tw from 'tailwind-styled-components';

export const Wrapper = tw.div`
  relative
  bg-[#ECF1F8] 
  w-[100vw] 
  h-[100vh] 
  flex 
  flex-col
  items-center
`;

export const PageContainer = tw.div`
  bg-[#ECF1F8] 
  w-full 
  h-[100vh] 
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
  text-[#4771B7] 
  text-[48px] 
  font-semibold 
  text-center 
  mb-[67px]
`;

export const SubText = tw.p`
  text-[32px] 
  mb-[67px] 
  text-center
`;

export const HomeBtn = tw.button`
  w-[330px] 
  h-[65px] 
  text-[25px] 
  font-medium 
  border 
  shadow-md 
  shadow-[#8F9296] 
  bg-white 
  text-center 
  rounded-[30px]
  cursor-pointer
`;
