import React from 'react';
import Header from './components/Header/Header';
import CategoryRouter from './router/CategoryRouter';
import Categorybar from './components/Categorybar/Categorybar';

function App() {
  return (
    <>
      <Header />
      <Categorybar />
      <CategoryRouter />
    </>
  );
}

export default App;
