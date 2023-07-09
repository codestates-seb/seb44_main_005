import React from 'react';
import { Route, Routes } from 'react-router-dom';

import CategoryPage from '../pages/CategoryPage';
import CategoryDetail from '../pages/CategoryDetail';

function CategoryRouter() {
  return (
    <Routes>
      <Route path="/category" element={<CategoryPage />} />
      <Route path="/category/:id" element={<CategoryDetail />} />
    </Routes>
  );
}

export default CategoryRouter;
