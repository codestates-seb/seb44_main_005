import tw from "tailwind-styled-components";

export const ImageInput = tw.label`
  bg-[#4771B7]
  px-6 py-2
  text-sm text-white
  rounded-[10px]
`;

export const PreviewBox = tw.div`
  w-[230px] h-[230px]
  border-[1px] border-[#4771B7]
  mt-5 py-24
  text-center
`;

export const PreviewImg = tw.img`
  w-[230px] h-[230px]
  object-cover
  mt-3 mb-3
  border-[1px] border-[#4771B7]
`;

export const DeleteImgBtn = tw.button`
  mx-auto px-4 py-1
  block
  bg-[#EDF1F8]
  border-[1px] border-black rounded-[5px]
  text-sm
`;