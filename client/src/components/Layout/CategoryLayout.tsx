import React from 'react';
import { Outlet } from 'react-router-dom';

import Categorybar from '../Categorybar/Categorybar';

function CategoryLayout() {
  return (
    <>
      <Categorybar />
      <Outlet />
    </>
  );
}

export default CategoryLayout;
