import tw from 'tailwind-styled-components';

export const SearchbarContainer = tw.div`
  relative
`;

export const SearchbarInput = tw.input`
  z-50
  border
  border-[#4771B7]
  rounded-[30px]
  w-[500px]
  h-[40px]
  pl-5
  text-sm
`;

export const AutoSearchContainer = tw.div`
  z-30 
  border-[0.5px] 
  border-t-none 
  border-[#4771B7] 
  absolute 
  h-auto 
  w-[470px] 
  bg-white 
  p-[8px] 
  top-[40px] 
  left-[15px] 
  rounded-b-lg
`;

export const AutoSearchData = tw.li`
  z-20 
  relative 
  px-[8px] 
  py-[4px] 
  w-full 
  text-[14px] 
  font-medium 
  hover:bg-[#EDF1F8] 
  hover:text-[#4771B7] 
  cursor-pointer 
  tracking-[0.5px]
`;

export const SearchIcon = tw.img`
  absolute 
  w-[16px] 
  right-[5px] 
  top-1.5
`;
