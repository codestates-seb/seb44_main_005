import tw from "tailwind-styled-components";

export const CarouselBox = tw.div`
  w-[400vw]
  flex
  overflow-hidden
  duration-1000
`;

export const LeftArrow = tw.div`
  absolute top-[50%] left-[3%]
  translate-y-[-50%]
  cursor-pointer
`;

export const RightArrow = tw.div`
  absolute top-[50%] right-[3%]
  translate-y-[-50%]
  cursor-pointer
`;