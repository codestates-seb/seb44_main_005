
import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";

import { AddBtn, StoreAddSection } from "../styles/StoreAdd/StoreAdd";
import AddProduct from "../components/StoreAdd/AddProduct";
import AddImages from "../components/StoreAdd/AddImages";
import StoreAddTop from "../components/StoreAdd/StoreAddTop";
import {
  DetailImgsState,
  FirstImgState,
  StoreformState,
  pageTitleState
} from '../store/StoreAdd';

function StoreAdd() {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const setPageTitle = useSetRecoilState(pageTitleState);
  const [form, setForm] = useRecoilState(StoreformState);
  const sendFirstImg = useRecoilValue(FirstImgState);
  const sendDetailImgs = useRecoilValue(DetailImgsState);
  const location = useLocation();
  const navigate = useNavigate();

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

  const storeContentPost = async () => {
    console.log(form);

    const imgForm = new FormData();
    sendDetailImgs.forEach((img) => imgForm.append(`images`, img));
    imgForm.append('thumbnailImage', sendFirstImg);

    const res = await fetch(`${API_URL}/stores`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJwaG9uZU51bWJlciI6IjAxMC0xMTExLTIyMjIiLCJyb2xlcyI6WyJQQVJUTkVSIl0sIm5pY2tuYW1lIjoidGFld29vQmFibyIsInVzZXJuYW1lIjoiaGdkQGdtYWlsLmNvbSIsIm1lbWJlcklkIjoxLCJzdWIiOiJoZ2RAZ21haWwuY29tIiwiaWF0IjoxNjg5MzE4MTg4LCJleHAiOjE2ODkzMjQxODh9.1BBoGwN6bZGB8_ygLb13Rw1S0KjtowXI0_idbqPtn_F6XJtYn-_wDeXRO_DJ8ZhyrChsww_ZXXq_OWm0sBuhpg'
      },
      body: JSON.stringify(form)
    });
    const json = await res.json();
    await fetch(`${API_URL}/storeImages/${json.storeId}`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJwaG9uZU51bWJlciI6IjAxMC0xMTExLTIyMjIiLCJyb2xlcyI6WyJQQVJUTkVSIl0sIm5pY2tuYW1lIjoidGFld29vQmFibyIsInVzZXJuYW1lIjoiaGdkQGdtYWlsLmNvbSIsIm1lbWJlcklkIjoxLCJzdWIiOiJoZ2RAZ21haWwuY29tIiwiaWF0IjoxNjg5MzE4MTg4LCJleHAiOjE2ODkzMjQxODh9.1BBoGwN6bZGB8_ygLb13Rw1S0KjtowXI0_idbqPtn_F6XJtYn-_wDeXRO_DJ8ZhyrChsww_ZXXq_OWm0sBuhpg'
      },
      body: imgForm
    });
    navigate(`/category/${json.storeId}`);
  }

  useEffect(() => {
    const path = location.pathname.substring(6);
    if (path === "/edit") {
      setPageTitle("업체 수정하기");
    }
  }, [])

  return (
    <StoreAddSection>
      <StoreAddTop formChangeHandler={formChangeHandler} />
      <AddProduct />
      <AddImages />
      <AddBtn type="button" onClick={() => {
        storeContentPost();
      }}>등록하기</AddBtn>
    </StoreAddSection>
  );
}

export default StoreAdd;
