import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Partner from './components/Partner/Partner';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/partner" element={<Partner />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App;
