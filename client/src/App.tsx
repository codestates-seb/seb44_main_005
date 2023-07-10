import Header from './components/Header/Header';
import MainRouter from './router/MainRouter';
import CategoryRouter from './router/CategoryRouter';
import MyRouter from './router/MyRouter';

function App() {
  return (
    <>
      <Header />
      <MainRouter />
      <CategoryRouter />
      <MyRouter />
    </>
  );
}

export default App;
