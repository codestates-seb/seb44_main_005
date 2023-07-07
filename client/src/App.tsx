import React from 'react';
import Header from './components/Header/Header';
import MainRouter from './router/MainRouter';
import CategoryRouter from './router/CategoryRouter';

function App() {
  return (
    <>
      <Header />
      <MainRouter />
      <CategoryRouter />
    </>
  );
}

export default App;
