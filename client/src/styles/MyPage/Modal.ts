import tw from 'tailwind-styled-components';

export const ModalAllContainer = tw.div`
    fixed
    top-0
    left-0
    right-0
    bottom-0
    flex
    justify-center
    items-center
    bg-black
    bg-opacity-40
    z-50
`;

export const ModalBoxContainer = tw.div`
    flex
    flex-col
    w-[600px]
    h-[500px]
    space-y-[20px]
    rounded-lg
    bg-white
    drop-shadow-lg
`;

export const CloseButtonContainer = tw.div`
    flex
    justify-end
    p-2
`;

export const CloseButton = tw.img`
    cursor-pointer
`;

export const LogoContainer = tw.div`
    flex
    justify-center
    py-12
`;

export const Logo = tw.img`
    ml-[40px]
    w-[182px]
`;

export const EditContainer = tw.div`
    flex
    flex-row
    justify-center
    space-x-[30px]
`;

export const EditInput = tw.input`
    border-[2px]
    border-[#CCCCCC]
    rounded-[8px]
    w-[255px]
    py-1
    px-3
`;

export const EditMessage = tw.div`
    flex
    flex-row
    justify-center
    text-sm
    pl-[80px]
`;

export const EditComplete = tw.div`
    flex
    justify-center
    pt-[30px]
`;

export const EditConfirmButton = tw.button`
    bg-[#4771B7]
    text-white
    p-3
    rounded-lg
`;