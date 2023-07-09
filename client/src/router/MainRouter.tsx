import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Welcome from '../pages/Welcome';
import Home from '../pages/Home';
import Login from '../pages/Login';
import Register from '../pages/Register';
import Partner from '../pages/Partner';
import CategoryLayout from '../components/Layout/CategoryLayout';
import MyPage from '../pages/MyPage';
import FooterLayout from '../components/Layout/FooterLayout';

function MainRouter() {
  return (
    <Routes>
      <Route path="/" element={<Welcome />} />
      <Route element={<FooterLayout />}>
        <Route path="/my" element={<MyPage />} />
        <Route path="/partner" element={<Partner />} />
        <Route element={<CategoryLayout />}>
          <Route path="/home" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
        </Route>
      </Route>
    </Routes>
  );
}

export default MainRouter;
