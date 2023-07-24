import tw from 'tailwind-styled-components';

export const StoreCheckContainer = tw.div`
    border-[1px]
    border-[#4771B7]
    px-[50px]
    py-16
    space-y-5
    w-[902px]
    h-[800px]
    overflow-y-auto
`;

export const StoreCheckTitle = tw.span`
    font-medium
    text-2xl
    pl-4
`;

export const StoreCards = tw.div`
    border-[1.5px]
    border-[#4771B7]
    w-[780px]
    h-[200px]
    rounded-lg
    p-5
    flex
    flex-row
    justify-center
    items-center
    space-x-12
`;

export const ImgContainer = tw.div`
    relative
    w-[146px]
    h-[127px]
`;

export const ImgInnerContainer = tw.div`
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

export const StoreInfoContainer = tw.div`
    flex
    flex-col
    w-[100%]
    space-y-20
`;

export const StoreName = tw.span`
    text-[20px]
    font-semibold
    cursor-pointer
`;

export const ButtonsContainer = tw.div`
    flex
    justify-end
    space-x-3
    font-medium
    text-[15px]
`;

export const StoreButtonStyle = tw.button`
    bg-[#F3F5F7]
    p-2
    rounded-lg
`;

export const NoStores = tw.div`
    flex
    flex-col
    justify-center
    items-center
    space-y-5
    p-20
    h-[600px]
`;