import tw from 'tailwind-styled-components';

export const WishContainer = tw.div`
    border-[1px]
    border-[#4771B7]
    py-16
    px-[35px]
    h-[800px]
    w-[902px]
    overflow-y-auto
`;

export const NoWishList = tw.div`
    flex
    flex-col
    justify-center
    items-center
    space-y-5
    p-20
    h-[600px]
`;

export const NoWishImgSize = tw.img`
    w-[100px]
`;

export const NoWishTitle = tw.p`
    text-2xl
    font-semibold
    pt-12
    pl-4
`;

export const WishCountTitle = tw.span`
    font-semibold
    text-2xl
    pl-4
`;