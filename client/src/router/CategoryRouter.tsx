import React from 'react';
import { Route, Routes } from 'react-router-dom';
import CategoryPage from '../pages/CategoryPage';

function CategoryRouter() {
  return (
    <Routes>
      <Route path="/category" element={<CategoryPage />} />
    </Routes>
  );
}

export default CategoryRouter;
