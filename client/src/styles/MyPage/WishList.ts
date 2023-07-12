import tw from 'tailwind-styled-components';

export const WishContainer = tw.div`
    border-[1px]
    border-[#4771B7]
    p-10
    max-h-[800px]
    overflow-y-auto
`;

export const NoWishList = tw.div`
    flex
    flex-col
    justify-center
    items-center
    space-y-5
    border-[1px]
    border-[#4771B7]
    p-20
`;

export const NoWishImgSize = tw.img`
    w-[100px]
`;

export const NoWishTitle = tw.p`
    text-2xl
    font-semibold
    pt-12
`;

export const WishCountTitle = tw.span`
    font-semibold
    text-2xl
`;