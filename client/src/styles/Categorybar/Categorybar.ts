import tw from 'tailwind-styled-components';

export const Item = tw.div`
  flex
  flex-col
  justify-center 
  items-center
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
