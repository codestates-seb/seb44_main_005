import React from 'react';
import MainRouter from './router/MainRouter';
import CategoryRouter from './router/CategoryRouter';
import MyRouter from './router/MyRouter';

function App() {
  return (
    <>
      <MainRouter />
      <CategoryRouter />
      <MyRouter />
    </>
  );
}

export default App;
