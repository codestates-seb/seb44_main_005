import tw from 'tailwind-styled-components';

export const InputContainer = tw.div`
  flex 
  flex-col 
  items-center
  mb-[40px]
`;

export const StyleContainer = tw.form`
  flex 
  justify-center 
  items-center
`;

export const LoginContainer = tw.form`
  flex 
  flex-col 
  items-center 
  justify-center 
  w-[400px] 
  h-[500px] 
  border 
  rounded-[10px] 
  border-[#4771B7] 
  bg-[#EDF1F8]
`;

export const IntroText = tw.p`
  text-center  
  text-[20px] 
  font-medium 
  my-[30px]
`;
