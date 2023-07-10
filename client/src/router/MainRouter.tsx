import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Welcome from '../pages/Welcome';
import Home from '../pages/Home';
import Login from '../pages/Login';
import Rigister from '../pages/Register';
import Partner from '../pages/Partner';
import CategoryLayout from '../components/Layout/CategoryLayout';
import MyPage from '../pages/MyPage';

function MainRouter() {
  return (
    <Routes>
      <Route element={<CategoryLayout />}>
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/rigister" element={<Rigister />} />
      </Route>
      <Route path="/" element={<Welcome />} />
      <Route path="/partner" element={<Partner />} />
      <Route path="/my/*" element={<MyPage />} />
    </Routes>
  );
}

export default MainRouter;
