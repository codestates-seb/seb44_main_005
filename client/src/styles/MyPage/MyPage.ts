import tw from 'tailwind-styled-components';

export const MyPageContainer = tw.div`
    flex
    flex-row
    justify-center
    max-w-[902px]
    min-h-[800px]
`;

export const MyBioContainer = tw.div`
    border-[1px]
    border-[#4771B7]
    w-[902px]
`;

export const MySpace = tw.div`
    space-y-4
    pt-5
    pb-10
    px-10
`;

export const ButtonGridEdit = tw.div`
    grid
    justify-items-end
    pt-5
`;

export const ButtonGrid = tw.div`
    grid
    justify-items-end
`;

export const ButtonStyle = tw.button`
    bg-[#4771B7]
    text-white
    px-4
    py-1
    rounded
`;

export const MiniButtonGrid = tw.div`
    flex
    space-x-3
`;

export const PhotoInputStyle = tw.span`
    text-[12px]
    bg-[#EDF1F8]
    border-[1px]
    border-[#4771B7]
    rounded
    p-2
    cursor-pointer
`;

export const ImgStyle = tw.img`
    w-[100px]
    h-[100px]
    rounded-full
    object-cover
`;

export const TopSpace = tw.div`
    flex
    flex-col
    items-center
    space-y-4
`;

export const NicknameAccent = tw.span`
    font-medium
    text-xl
`;

export const LoadingContainer = tw.div`
  border-[1px]
  border-[#4771B7]
  w-[902px]
  h-[800px]
  flex
  flex-col
  justify-center
  items-center
  text-3xl
  font-semibold
  space-y-5
`;