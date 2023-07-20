import tw from 'tailwind-styled-components';

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
    w-[180px]
`;