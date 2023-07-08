import React from 'react';
import { Route, Routes } from 'react-router-dom';

import CategoryPage from '../pages/CategoryPage';
import CategoryLayout from '../components/Layout/CategoryLayout';

function CategoryRouter() {
  return (
    <Routes>
      <Route element={<CategoryLayout />}>
        <Route path="/category" element={<CategoryPage />} />
      </Route>
    </Routes>
  );
}

export default CategoryRouter;
