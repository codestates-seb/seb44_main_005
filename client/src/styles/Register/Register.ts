import tw from 'tailwind-styled-components';

export const StyleContainer = tw.div`
  flex 
  justify-center 
  items-center
  mb-[50px]
  min-h-[60vh]
`;

export const RegisterContainer = tw.div`
  flex 
  flex-col 
  items-center 
  justify-center 
  w-[540px] 
  h-[550px] 
  border 
  rounded-[10px] 
  border-[#4771B7] 
  bg-[#EDF1F8]
`;

export const Message = tw.p`
  text-[12px] 
  pt-1 
  h-[20px] 
  text-red-500
`;

export const InputContainer = tw.div`
  pr-4
`;

export const Input = tw.input`
  border-[1px] 
  border-[#9A9A9A] 
  rounded-md 
  p-2 
  py-[2px] 
  w-[280px]
  h-[30px]
  text-[13px]
`;

export const Label = tw.label`
  mr-[15px]
`;
