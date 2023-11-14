import tw from 'tailwind-styled-components';

export const PageContainer = tw.div`
  relative
  bg-[#ECF1F8] 
  w-[100vw]
  h-[100vh] 
  flex 
  flex-col 
  items-center 
  justify-center
`;

export const MainText = tw.div`
  font-semibold 
  text-[3rem]
  text-[#4771B7] 
  my-[30px]
`;

export const SubText = tw.p`
  text-[30px] 
  mb-[30px]
`;
