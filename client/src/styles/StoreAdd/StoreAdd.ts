import tw from "tailwind-styled-components";

export const StoreAddSection = tw.section`
  w-[900px] min-h-[55vh]
  mx-auto my-20
  font-medium text-lg
`;

export const Input = tw.input`
  border-[1px] border-[#9A9A9A] rounded-[5px]
  flex-auto
  ml-5 p-3
  h-[45px]
`;
export const CategorySelect = tw.select`
  border-[1px] border-[#9A9A9A] rounded-[5px]
  flex-auto
  ml-5 px-3 py-2
  h-[45px]
`;

export const IntroContent = tw.textarea`
  border-[1px] border-[#9A9A9A] rounded-[5px]
  flex-auto
  ml-5 p-3
  h-[120px]
`;

export const InputTitle = tw.div`
  w-[130px] text-right
`;

export const SearchAddress = tw.button`
  px-5 py-2 ml-3
  bg-[#4771B7]
  rounded-[10px]
  text-white text-sm
`;

export const ProductBox = tw.div`
  w-[830px]
  pl-8 pr-10 py-7 ml-5
  bg-[#EDF1F8]
  flex-1
  rounded-[5px]
`;

export const ProductInputTitle = tw.div`
  w-[80px] text-right
`;

export const ProductAddBtn = tw.button`
  px-5 py-2
  bg-[#4771B7]
  rounded-[10px]
  text-white text-sm
  block
  ml-auto
`;

export const Ticket = tw.div`
  ml-40 mb-5
  font-bold text-[#4771B7]
  flex
`;

export const TicketDeleteBtn = tw.button`
  px-3 py-1 ml-5
  border-[1px] border-black rounded-[5px]
  bg-[#EDF1F8]
  text-sm text-black
`;

export const AddBtn = tw.button`
  bg-[#4771B7]
  px-7 py-3 mx-auto mt-20
  block
  text-white text-lg
  rounded-[10px]
`;