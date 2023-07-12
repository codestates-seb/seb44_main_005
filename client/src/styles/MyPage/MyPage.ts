import tw from 'tailwind-styled-components';

export const MyPageContainer = tw.div`
    flex
    flex-row
    justify-center
    space-x-10
`;

export const MyBioContainer = tw.div`
    border-[1px]
    border-[#4771B7]
`;

export const MySpace = tw.div`
    space-y-7
    p-10
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

export const MiniButtonStyle = tw.button`
    text-[12px]
    bg-[#EDF1F8]
    border-[1px]
    border-[#4771B7]
    rounded
`;

export const ImgStyle = tw.img`
    w-[100px]
`;

export const TopSpace = tw.div`
    flex
    flex-col
    items-center
    space-y-2
`;

export const NicknameAccent = tw.span`
    font-medium
    text-xl
`;

export const UserInfo = tw.div`
    space-x-10
    text-lg
`;

export const UserInfoTitle = tw.span`
    font-medium
`;

export const BusinessSpace = tw.div`
    flex
    flex-row
    space-x-10
    justify-center
`;

export const BusinessCategory = tw.div`
    flex
    flex-col
    space-y-5
    text-lg
`;

export const BusinessCategoryTitle = tw.span`
    font-semibold
`;

export const WishContainer = tw.div`
    flex
    flex-col
    justify-center
    items-center
    space-y-5
    border-[1px]
    border-[#4771B7]
    p-16
`;

export const WishCount = tw.div`
    w-[100%]
    font-semibold
    text-2xl
`;

export const EachWishCard = tw.div`
    flex
    flex-row
    border-[1px]
    border-[#4771B7]/[.44]
    w-[680px]
`;

export const CardImgSize = tw.img`
    w-[250px]
    h-[200px]
    object-cover
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