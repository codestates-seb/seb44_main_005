import { Routes, Route } from 'react-router-dom';

import FooterLayout from '../components/Layout/FooterLayout';
import StoreAdd from '../pages/StoreAdd';


function MyRouter() {
  return (
    <Routes>
      <Route element={<FooterLayout />}>
        <Route path="/store/add" element={<StoreAdd />} />
      </Route>
    </Routes>
  );
}

export default MyRouter;