import tw from "tailwind-styled-components";

export const CarouselSection = tw.section`
  w-[100vw] h-[400px]
  mb-5
  flex justify-center items-center
`;

export const CarouselImg = tw.img`
  w-[400px] h-[300px]
  object-cover
  rounded-[10px]
  shadow-[2px_3px_4px_3px_rgba(0,0,0,0.4)]
  mr-20
`;