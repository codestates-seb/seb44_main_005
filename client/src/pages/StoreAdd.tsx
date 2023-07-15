
import { useEffect, useState } from 'react';
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom';
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";

import { AddBtn, StoreAddSection } from "../styles/StoreAdd/StoreAdd";
import AddProduct from "../components/StoreAdd/AddProduct";
import AddImages from "../components/StoreAdd/AddImages";
import StoreAddTop from "../components/StoreAdd/StoreAddTop";
import {
  DetailImgsState,
  FirstImgState,
  SendDetailImgsState,
  SendFirstImgState,
  StoreformState,
  pageTitleState
} from '../store/storeAddAtom';

function StoreAdd() {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const [form, setForm] = useRecoilState(StoreformState);
  const [btnText, setBtnText] = useState(true);
  const sendFirstImg = useRecoilValue(SendFirstImgState);
  const sendDetailImgs = useRecoilValue(SendDetailImgsState);
  const setPageTitle = useSetRecoilState(pageTitleState);
  const setFirstImg = useSetRecoilState(FirstImgState);
  const setDetailImgs = useSetRecoilState(DetailImgsState);
  const location = useLocation();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const storeId = searchParams.get('store_id');
  const accessToken = sessionStorage.getItem('Authorization');

  const formChangeHandler = (e) => {
    if (e.target.name === "storeName") {
      setForm({...form, storeName: e.target.value});
    }
    else if (e.target.name === "body") {
      setForm({...form, body: e.target.value});
    }
    else if (e.target.name === "kakao") {
      setForm({...form, kakao: e.target.value});
    }
    else if (e.target.name === "contact") {
      setForm({...form, contact: e.target.value});  
    }
    else if (e.target.name === "category") {
      setForm({...form, category: e.target.value});
    }
    console.log(form);
  }

  const storeAddPost = async () => {
    console.log(form);

    const imgForm = new FormData();
    sendDetailImgs.forEach((img) => imgForm.append(`images`, img));
    imgForm.append('thumbnailImage', sendFirstImg);
    try {
      const res = await fetch(`${API_URL}/stores`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': accessToken
        },
        body: JSON.stringify(form)
      });
      const json = await res.json();
      await fetch(`${API_URL}/storeImages/${json.storeId}`, {
        method: 'POST',
        headers: {
          'Authorization': accessToken
        },
        body: imgForm
      });
      navigate(`/category/${json.storeId}`);
    }
    catch(error) {
      console.log(error);
    }
  }

  const storeEditFetch = async (storeId: string) => {
    try {
      const res = await fetch(`${API_URL}/stores/${storeId}`);
      const json = await res.json();
      setForm({
        storeName: json.storeName,
        body: json.body,
        address: json.address,
        kakao: json.kakao,
        contact: json.contact,
        category: json.category,
        items: json.items
      })
      setFirstImg(json.storeImages[0]);
      setDetailImgs(json.storeImages.slice(1));
    }
    catch(error) {
      console.log(error);
    }
  }

  const storeEditPatch = async (storeId: string) => {
    const imgForm = new FormData();
    sendDetailImgs.forEach((img) => imgForm.append(`images`, img));
    imgForm.append('thumbnailImage', sendFirstImg);
    try {
      await fetch(`${API_URL}/stores/${storeId}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': accessToken
        },
        body: JSON.stringify(form)
      });
      await fetch(`${API_URL}/storeImages/${storeId}`, {
        method: 'PATCH',
        headers: {
          'Authorization': accessToken
        },
        body: imgForm
      });
      navigate(`/category/${storeId}`);
    }
    catch(error) {
      console.log(error);
    }
  }

  useEffect(() => {
    const path = location.pathname.substring(6);
    if (path === '/edit') {
      setPageTitle('업체 수정하기');
      setBtnText((prev) => !prev);
      storeEditFetch(storeId);
    }
  }, [])

  return (
    <StoreAddSection>
      <StoreAddTop formChangeHandler={formChangeHandler} />
      <AddProduct />
      <AddImages />
      <AddBtn type="button" onClick={() => {
        if (btnText) {
          storeAddPost();
        }
        else {
          storeEditPatch(storeId);
        }
      }}>{btnText ? '등록하기' : '수정하기'}</AddBtn>
    </StoreAddSection>
  );
}

export default StoreAdd;
