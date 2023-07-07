import tw from 'tailwind-styled-components';

export const HaederContainer = tw.header`
  flex
  m-5
  justify-between
`;

export const LogoContainer = tw.div`
  flex
  items-center
`;

export const LoginContainer = tw.div`
  flex
  justify-around
  items-center
  w-[200px]
  text-sm
`;

export const UnLoginContainer = tw.div`
  flex
  justify-around
  w-[400px]
  text-sm
  leading-0
  items-center
`;
