import { useEffect } from 'react';
import { atom, useRecoilState, useSetRecoilState } from 'recoil';

import { AddBtn, StoreAddSection } from '../styles/StoreAdd/StoreAdd';
import AddProduct from '../components/StoreAdd/AddProduct';
import AddImages from '../components/StoreAdd/AddImages';
import StoreAddTop from '../components/StoreAdd/StoreAddTop';
import { useLocation } from 'react-router-dom';

export const formState = atom({
  key: 'formState',
  default: {},
});

export const pageTitleState = atom({
  key: 'pageTitleState',
  default: '업체 등록하기',
});

function StoreAdd() {
  const setPageTitle = useSetRecoilState(pageTitleState);
  const [form, setForm] = useRecoilState(formState);
  const location = useLocation();

  const formChangeHandler = (e) => {
    if (e.target.name === 'storeName') {
      setForm({ ...form, storeName: e.target.value });
    } else if (e.target.name === 'content') {
      setForm({ ...form, content: e.target.value });
    } else if (e.target.name === 'kakao') {
      setForm({ ...form, kakao: e.target.value });
    } else if (e.target.name === 'contact') {
      setForm({ ...form, contact: e.target.value });
    } else if (e.target.name === 'category') {
      setForm({ ...form, category: e.target.value });
    }
  };

  useEffect(() => {
    console.log(location);
    const path = location.pathname.substring(6);
    if (path === '/edit') {
      setPageTitle('업체 수정하기');
    }
  }, []);

  return (
    <StoreAddSection>
      <StoreAddTop formChangeHandler={formChangeHandler} />
      <AddProduct />
      <AddImages />
      <AddBtn type="button">등록하기</AddBtn>
    </StoreAddSection>
  );
}

export default StoreAdd;
