import tw from 'tailwind-styled-components';

export const HaederContainer = tw.header`
  h-[8vh]
  flex
  justify-between
  border-b-[0.5px]
  border-[#4771B7]
  w-full
`;

export const LogoContainer = tw.div`
  flex
  items-center
  ml-5
`;

export const LoginContainer = tw.div`
  flex
  justify-center
  items-center
  w-[200px]
  text-sm
`;

export const UnLoginContainer = tw.div`
  flex
  justify-center
  w-[400px]
  text-sm
  leading-0
  items-center
`;

export const ProfileIcon = tw.img`
  w-[30px] 
  ml-[40px]
  transition duration-500 ease-in-out
  focus:translate-y-6
`;
