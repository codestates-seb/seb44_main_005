import tw from "tailwind-styled-components";

export const ReviewLeftBox = tw.div`
  w-[190px] h-[150px]
  bg-[#EDF1F8]
  mr-[10px] px-5 py-10
  rounded-[5px]
`
;
export const ReviewRightBox = tw.div`
  w-[400px] h-[150px]
  bg-[#EDF1F8]
  rounded-[5px]
  p-5
`;

export const ReviewBtn = tw.button`
  bg-[#4771B7]
  px-3 py-1
  text-white
  rounded-[5px]
`;

export const ReviewTextarea = tw.textarea`
  mt-3 p-2
  w-full h-[70px]
  border-[1px] border-[#4771B7]
  rounded-[5px]
  text-sm
  font-medium
`;

export const Stars = tw.span`
  text-[#4471B7] text-lg
  cursor-pointer
`;