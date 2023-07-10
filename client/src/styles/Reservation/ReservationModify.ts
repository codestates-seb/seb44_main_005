import tw from 'tailwind-styled-components';

export const ReservationContainer = tw.div`
  h-[100vh]
  w-[600px]
  text-start
  ml-[300px]
  mt-[80px]
`;

export const Title = tw.div`
  font-medium pb-3 text-xl border-b-[1px] border-[#4771B7]
`;

export const SubTitle = tw.div`
  text-lg 
  font-medium 
  my-5
`;

export const ReservationInfo = tw.section`
  w-[600px] 
  mt-[100px]
`;

export const InfoTitle = tw.div`
  mr-5
  text-base
  font-medium
`;

export const ReservationTitle = tw.div`
  text-3xl font-medium
  pb-3 mb-10
`;

export const ReservationInput = tw.input`
  border-[1px] border-[#CCCCCC] rounded-[5px]
  w-[240px] h-[38px]
  p-2
`;

export const InputRequire = tw.div`
  absolute top-2 right-2
  text-[#FF0000] font-medium
`;

export const PaymentInfoBox = tw.section`
  sticky top-10
  w-[300px] h-[500px]
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
  w-[250px] h-[135px]
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
