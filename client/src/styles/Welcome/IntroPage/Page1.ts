import tw from 'tailwind-styled-components';

export const PageContainer = tw.div`
  relative
  w-[100vw]
  h-[100vh] 
`;

export const IntroText = tw.div`
  h-full
  flex flex-col justify-center
  ml-[150px]
`;

export const ClickContainer = tw.div`
  flex 
  flex-col 
  items-center 
  justify-center
  absolute
  left-[50%]
  bottom-5
  translate-x-[-50%]
`;

export const HomeBtn = tw.span`
  font-semibold 
  text-[25px] 
  mb-[10px]
`;
