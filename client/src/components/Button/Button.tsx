import React from 'react';

type BProps = {
  children: React.ReactNode;
  color?: string;
  bgColor?: string;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
};
function Button({ children, color, bgColor, onClick }: BProps) {
  return (
    <button
      className={`border
  border-[#D8D8D8]
  h-[35px]
  w-[255px]
  my-[4px]
  cursor-pointer
  text-[13px]
  py-[5px]
  rounded-[10px]
  font-semibold
  text-center
  bg-[${bgColor}]
  text-[${color}]
  `}
    >
      {children}
    </button>
  );
}

export default Button;
