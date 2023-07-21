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
  const [isLoading, setIsLoading] = useState(false);
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

  const formChangeHandler = (e) => { // 스위치문으로 추천
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
  };

  const storeAddPost = async () => {
    if (!form.storeName) {
      return alert('업체명을 입력해주세요.');
    }
    else if (!form.body) {
      return alert('소개글을 입력해주세요.');
    }
    else if (!form.address) {
      return alert('주소를 선택해주세요.');
    }
    else if (!form.kakao) {
      return alert('카카오톡 ID를 입력해주세요.');
    }
    else if (!form.contact) {
      return alert('전화번호를 입력해주세요.');
    }
    else if (!form.category) {
      return alert('카테고리를 선택해주세요.');
    }
    else if (!form.items.length) {
      return alert('상품을 1개 이상 등록해주세요.');
    }
    else if (!sendFirstImg) {
      return alert('대표사진을 등록해주세요.');
    }
    else if (sendDetailImgs.length < 3) {
      return alert('상세 이미지를 최소 3장 이상 등록해 주세요.');
    }
    const imgForm = new FormData();
    sendDetailImgs.forEach((img) => imgForm.append(`images`, img));
    imgForm.append('thumbnailImage', sendFirstImg);
    try {
      setIsLoading((prev) => !prev);
      const res = await fetch(`${API_URL}/stores`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': accessToken
        },
        body: JSON.stringify(form)
      });
      if (!res.ok) {
        setIsLoading((prev) => !prev);
        return alert('업체 등록에 실패했습니다.');
      }
      const json = await res.json();
      const imgRes = await fetch(`${API_URL}/storeImages/${json.storeId}`, {
        method: 'POST',
        headers: {
          'Authorization': accessToken
        },
        body: imgForm
      });
      if (!imgRes.ok) {
        setIsLoading((prev) => !prev);
        return alert('업체 이미지 등록에 실패했습니다.');
      }
      setIsLoading((prev) => !prev);
      navigate(`/category/${json.storeId}`);
      window.location.reload();
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
      setIsLoading((prev) => !prev);
      const res = await fetch(`${API_URL}/stores/${storeId}`, { // 동기 필요없음
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': accessToken
        },
        body: JSON.stringify(form)
      });
      if (!res.ok) {
        setIsLoading((prev) => !prev);
        return alert('업체 등록에 실패했습니다.');
      }
      const imgRes = await fetch(`${API_URL}/storeImages/${storeId}`, {
        method: 'PATCH',
        headers: {
          'Authorization': accessToken
        },
        body: imgForm
      });
      if (!imgRes.ok) {
        setIsLoading((prev) => !prev);
        return alert('업체 등록에 실패했습니다.');
      }
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
  }, []);

  return (
    <StoreAddSection>
      {isLoading ? 
        <div className="text-center text-2xl font-bold">업체 등록/수정 중입니다...</div> :
        <>
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
        </>
      }
    </StoreAddSection>
  );
}

export default StoreAdd;
