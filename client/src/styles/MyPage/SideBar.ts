import tw from 'tailwind-styled-components';

export const SideContainer = tw.div`
    border-[1px]
    border-[#4771B7]
    h-[500px]
    w-[270px]
    px-10
    py-16
    space-y-20
    mr-10
`;

export const SideSpace = tw.div`
    flex
    flex-col
    space-y-8
`;

export const SideTitle = tw.span`
    text-xl
    font-semibold
`;

export const SideList = tw.div`
    text-lg
    flex
    flex-col
    space-y-8
    pl-5
`;

export const SideLink = tw.a`
  text-base
  text-black
  hover:text-[#3366BB]
  cursor-pointer
`;