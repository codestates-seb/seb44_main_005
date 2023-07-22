import tw from "tailwind-styled-components";

export const PaymentInfoBox = tw.section`
  sticky top-10
  w-[300px] h-[635px]
  border-[1px] border-[#4771B7] rounded-[5px]
  mt-10 ml-20
  font-medium
`;

export const AmountBox = tw.div`
  bg-[#E7EDF6]
  text-[#4771B7] font-semibold
  flex justify-between
  px-7 py-5
`;

export const RuleBox = tw.div`
  w-[250px] h-[170px]
  bg-[#E7EDF6]
  rounded-[5px]
  mx-5 my-7 p-3
`;

export const PaymentButton = tw.button`
  bg-[#4771B7]
  w-[250px] h-[50px]
  block mx-auto p-2
  text-white
  rounded-[10px]
`;

export const WishButton = tw.button`
  w-[250px] h-[50px]
  block
  mx-auto mt-5 mb-7 p-2
  rounded-[10px] border-[1px] border-[#4771B7]
  font-semibold
`;

export const InquiryBox = tw.div`
  border-t-[1px] border-[#4771B7]
  flex justify-center
  text-sm
  p-2
`;

export const StoreProfile = tw.img`
  w-[38px] h-[38px]
  object-cover
  rounded-full
  mr-3
`;