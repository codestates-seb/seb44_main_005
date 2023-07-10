import React from 'react';

import Header from './components/Header/Header';
import MainRouter from './router/MainRouter';
import CategoryRouter from './router/CategoryRouter';
import Categorybar from './components/Categorybar/Categorybar';

function App() {
  return (
    <>
      <MainRouter />
      <CategoryRouter />
    </>
  );
}

export default App;
