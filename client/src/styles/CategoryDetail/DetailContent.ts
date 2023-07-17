import tw from "tailwind-styled-components";

export const DetailCategoryName = tw.div`
  text-xl text-[#787878] font-medium
  mb-1
`;

export const DetailTitle = tw.div`
  text-3xl font-bold
  border-b-[1px] border-[#4771B7]
  pb-3 mb-10
`;

export const ImgBox = tw.div`
  flex flex-wrap justify-between
  w-[570px]
  mx-auto
`;

export const ThumbnailImg = tw.img`
  w-[570px] h-[400px]
  mb-2
  mx-auto
  object-cover
`;

export const DetailImgs = tw.img`
  w-[280px] h-[200px]
  object-cover
`;

export const StoreInfoBox = tw.div`
  mt-10 p-5
  font-medium
  border-[1px] border-[#4771B7]
  rounded-[5px]
`;
