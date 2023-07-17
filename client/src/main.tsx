import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.tsx';
import './index.css';
import { RecoilRoot } from 'recoil';
import { BrowserRouter } from 'react-router-dom';

const recoilPersistConfig = {
  key: 'recoil-persist', // 로컬 스토리지에 저장될 키
  storage: localStorage, // 사용할 스토리지 (로컬 스토리지, 세션 스토리지 등)
};

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <BrowserRouter>
    <React.Suspense fallback={<div>Loading...</div>}>
      <RecoilRoot {...recoilPersistConfig}>
        <App />
      </RecoilRoot>
    </React.Suspense>
  </BrowserRouter>
);
