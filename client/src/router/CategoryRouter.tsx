import { Route, Routes } from 'react-router-dom';
import { motion } from 'framer-motion';

import CategoryPage from '../pages/CategoryPage';
import CategoryDetail from '../pages/CategoryDetail';
import CategoryLayout from '../components/Layout/CategoryLayout';
import FooterLayout from '../components/Layout/FooterLayout';
import HeaderLayout from '../components/Layout/HeaderLayout';
import Payments from '../pages/Payments';
import PaymentSuccess from '../pages/PaymentSuccess';

function CategoryRouter() {
  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.5 }}
      animate={{ opacity: 1, scale: 1 }}
      transition={{
        duration: 0.8,
        delay: 0.5,
        ease: [0, 0.71, 0.2, 1.01],
      }}
    >
      <Routes>
        <Route element={<HeaderLayout />}>
          <Route element={<FooterLayout />}>
            <Route element={<CategoryLayout />}>
              <Route path="/category" element={<CategoryPage />} />
              <Route path="/category/:id" element={<CategoryDetail />} />
            </Route>
            <Route path="/search" element={<CategoryPage />} />
            <Route path="/store/payment/success" element={<PaymentSuccess />} />
          </Route>
        </Route>
        <Route path="/store/payment/:store_id" element={<Payments />} />
      </Routes>
    </motion.div>
  );
}

export default CategoryRouter;
