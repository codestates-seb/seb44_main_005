import tw from "tailwind-styled-components"

export const AfterPaymentSection = tw.section`
  w-[600px] h-[77vh]
  mx-auto
  flex flex-col justify-center items-center
`;

export const CheckIcon = tw.div`
  bg-[#4771B7]
  w-[105px] h-[105px
  pr-2 pt-1
  rounded-full
`;

export const CloseIcon = tw.div`
  bg-[#4771B7]
  w-[105px] h-[105px]
  flex justify-center items-center
  rounded-full
`;

export const AfterPaymentContent = tw.div`
  px-10 py-7 mt-10
  border-[1px] border-[#4771B7] rounded-[5px]
  bg-[#E7EDF6]
  font-medium
`;

export const AfterPaymentBtn = tw.button`
  mt-10 px-5 py-2
  bg-[#4771B7]
  text-white
  rounded-[10px]
  duration-500
  hover:bg-[#65a4d8]
`;