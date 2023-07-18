import tw from 'tailwind-styled-components';

export const Item = tw.div`
  flex
  flex-col
  justify-center 
  items-center
  transition duration-500 ease-in-out
  hover:scale-110
`;

export const CategoryBarContainer = tw.div`
  flex 
  justify-center 
  items-center
  my-7
`;

export const CategoryBar = tw.div`
  flex 
  w-[700px] 
  justify-around 
  text-sm 
  grow-1
`;
