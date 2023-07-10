import React from 'react';

import { StyleButton } from '../../styles/Button/Button';

type BProps = {
  children: React.ReactNode;
  color?: string;
  bgColor?: string;
  clickHandler?: React.MouseEventHandler<HTMLButtonElement>;
};
function Button({ children, color, bgColor, clickHandler }: BProps) {
  const style = {
    backgroundColor: bgColor,
    color: color,
  };
  return <StyleButton style={style} onClick={clickHandler}>{children}</StyleButton>;
}

export default Button;
