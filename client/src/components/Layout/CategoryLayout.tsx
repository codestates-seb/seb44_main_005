import React from 'react';
import Categorybar from '../Categorybar/Categorybar';
import { Outlet } from 'react-router-dom';

function CategoryLayout() {
  return (
    <>
      <Categorybar />
      <Outlet />
    </>
  );
}

export default CategoryLayout;
