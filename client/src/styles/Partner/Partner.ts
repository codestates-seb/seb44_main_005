import tw from 'tailwind-styled-components';

export const PartnerContainer = tw.div`
    flex
    justify-center
    items-center
    min-h-[77vh]
`;

export const RegiContainer =  tw.div`
    flex
    flex-col
    justify-center
    w-[700px]
`;

export const RegiTitle = tw.p`
    text-2xl
    font-semibold
    justify-start
    p-10
`;

export const FormContainer = tw.form`
    justify-center
`;

export const RepreNameContainer = tw.div`
    flex
    space-x-5
    justify-end
    py-2
`;

export const CommonInput  = tw.input`
    border-[1px]
    border-[#9A9A9A]
    rounded-md 
    w-[78%]
    px-2
    py-[2px]
`;

export const RegiNumberContainer = tw.div`
    flex
    space-x-4
    justify-end
    py-2
`;

export const RegiNumberInput = tw.input`
    border-[1px]
    border-[#9A9A9A]
    rounded-md
    w-[100%]
    px-2
    py-[2px]
`;

export const RegiNumberNoWrite = tw.p`
    pt-1
    text-gray-950
`;

export const RegiNumberCorrect = tw.p`
    pt-1
    text-green-500
`;

export const RegiNumberConfirm = tw.p`
    pt-1
    text-amber-600
`;

export const RegiNumberWrong = tw.p`
    pt-1
    text-red-500
`;

export const CompanyName = tw.div`
    flex
    space-x-5
    justify-end
    py-2
`;

export const OpeningContainer = tw.div`
    flex
    space-x-5
    justify-end
    py-2
`;

export const BusinessContanier = tw.div`
    flex
    space-x-5
    justify-end
    w-[55%]
`;

export const LabelStyle = tw.label`
    w-[9%]
`;

export const BusinessInput = tw.input`
    border-[1px]
    border-[#9A9A9A]
    rounded-md
    w-[60%]
    px-2
    py-[2px]
`;

export const SectorContainer = tw.div`
    flex
    space-x-5
    justify-end
    w-[45%]
`;

export const BusinessSectorContainer = tw.div`
    flex
    flex-row
    justify-end
    py-2
`;

export const FormRegiButton = tw.button`
    font-semibold
    rounded-md
    text-xl
    px-14
    py-3
    bg-[#4771B7]
    text-white
`;

export const DisableFormRegiButton = tw.button`
    font-semibold
    text-xl
    px-14
    py-3
    opacity-50
    bg-[#4771B7]
    text-white
    cursor-default
`;

export const FormRegiContainer = tw.div`
    flex
    justify-center
    pt-4
`;

