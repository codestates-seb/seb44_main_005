import { Route, Routes } from 'react-router-dom';

import CategoryPage from '../pages/CategoryPage';
import CategoryDetail from '../pages/CategoryDetail';
import CategoryLayout from '../components/Layout/CategoryLayout';
import FooterLayout from '../components/Layout/FooterLayout';
import HeaderLayout from '../components/Layout/HeaderLayout';
import Payments from '../pages/Payments';

function CategoryRouter() {
  return (
    <Routes>
      <Route element={<HeaderLayout />}>
        <Route element={<FooterLayout />}>
          <Route element={<CategoryLayout />}>
            <Route path="/category" element={<CategoryPage />} />
            <Route path="/category/:id" element={<CategoryDetail />} />
          </Route>
        </Route>
      </Route>
      <Route path="/store/payment" element={<Payments />} />
    </Routes>
  );
}

export default CategoryRouter;
