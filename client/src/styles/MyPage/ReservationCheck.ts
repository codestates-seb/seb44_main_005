import tw from 'tailwind-styled-components';

export const ResCheckContainer = tw.div`
    border-[1px]
    border-[#4771B7]
    px-[50px]
    py-16
    space-y-5
`;

export const ResCheckTitle = tw.p`
    font-medium
    text-2xl
    pl-4
`;

export const ResCheckCards = tw.div`
    border-[1.5px]
    border-[#4771B7]
    w-[700px]
    h-[200px]
    rounded-lg
    p-5
    flex
    flex-row
    justify-center
    items-center
    space-x-12
`;

export const ResImgContainer = tw.div`
    relative
    w-[146px]
    h-[127px]
`;

export const ImgResSzing = tw.div`
    absolute
    inset-0
    w-[146px]
    h-[127px]
`;

export const ImgStyle = tw.img`
    w-full
    h-full
    object-cover
    rounded-md
`;

export const ResInfoContainer = tw.div`
    flex
    flex-col
    w-[100%]
    space-y-2
`;

export const StatusContainer = tw.div`
    flex
    justify-end
    w-[100%]
`;

export const ResInformation = tw.div`
    flex
    flex-col
    w-[100%]
    space-y-2
`;

export const ResDate = tw.span`
    text-[20px]
    font-semibold
`;

export const ResItemCount = tw.span`
    text-[15px]
    text-[#868686]
    font-medium
`;

export const ResTotalPrice = tw.span`
    text-[15px]
    font-semibold
`;

export const ResButtonsContainer = tw.div`
    flex
    justify-end
    space-x-3
    font-medium
    text-[15px]
`;

export const ButtonStyle = tw.button`
    bg-[#F3F5F7]
    p-2
    rounded-lg
`;

export const NoButtons = tw.p`
    h-[38.5px]
`;