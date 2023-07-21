import tw from 'tailwind-styled-components';

export const BusinessSpace = tw.div`
    flex
    flex-row
    space-x-16
    justify-center
    pt-[24px]
`;

export const BusinessCategory = tw.div`
    flex
    flex-col
    space-y-[24px]
    text-lg
`;

export const BusinessCategoryTitle = tw.span`
    font-semibold
    w-[120px]
`;