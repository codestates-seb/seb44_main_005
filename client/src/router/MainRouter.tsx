import { Routes, Route } from 'react-router-dom';
import { motion } from 'framer-motion';

import Welcome from '../pages/Welcome';
import Home from '../pages/Home';
import Login from '../pages/Login';
import Register from '../pages/Register';
import Partner from '../pages/Partner';
import CategoryLayout from '../components/Layout/CategoryLayout';
import FooterLayout from '../components/Layout/FooterLayout';
import HeaderLayout from '../components/Layout/HeaderLayout';

function MainRouter() {
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
        <Route path="/" element={<Welcome />} />
        <Route element={<HeaderLayout />}>
          <Route element={<FooterLayout />}>
            <Route path="/partner" element={<Partner />} />
            <Route element={<CategoryLayout />}>
              <Route path="/home" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
            </Route>
          </Route>
        </Route>
      </Routes>
    </motion.div>
  );
}

export default MainRouter;
