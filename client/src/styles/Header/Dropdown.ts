import tw from 'tailwind-styled-components';

export const StyledContainer = tw.ul`
  absolute
  w-[120px]
  top-[25px]
  right-[10px]
  text-center
  border
  border-[#4771B7]
  rounded-[5px]
  bg-[#D6DFEF] 
`;

export const Menu = tw.li`
  px-[25px]
  py-[10px]
  cursor-pointer
  hover:text-[#4771B7]
`;

export const DropDownContainer = tw.div`
relative
`;
