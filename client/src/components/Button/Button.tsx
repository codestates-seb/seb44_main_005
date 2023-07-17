import React from 'react';

import { StyleButton } from '../../styles/Button/Button';

type BProps = {
  children: React.ReactNode;
  color?: string;
  bgColor?: string;
  clickHandler?: React.MouseEventHandler<HTMLButtonElement>;
  disabled?: boolean;
};
function Button({ children, color, bgColor, clickHandler, disabled }: BProps) {
  const style = {
    backgroundColor: bgColor,
    color: color,
  };
  return (
    <StyleButton onClick={clickHandler} style={style} disabled={disabled}>
      {children}
    </StyleButton>
  );
}

export default Button;
