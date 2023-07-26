import { useEffect, useState } from 'react';
import { useLocation, useSearchParams } from 'react-router-dom';
import { useRecoilState, useSetRecoilState } from 'recoil';

import { AddBtn, StoreAddSection } from '../styles/StoreAdd/StoreAdd';
import AddProduct from '../components/StoreAdd/AddProduct';
import AddImages from '../components/StoreAdd/AddImages';
import StoreAddTop from '../components/StoreAdd/StoreAddTop';
import {
  DetailImgsState,
  FirstImgState,
  SendDetailImgsState,
  SendFirstImgState,
  StoreformState,
  pageTitleState,
} from '../store/storeAddAtom';
import { StoreAddFormType } from '../intefaces/StoreAdd';

function StoreAdd() {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const [form, setForm] = useRecoilState(StoreformState);
  const [fetchImgsCount, setFetchImgsCount] = useState(0);
  const [btnText, setBtnText] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  const [sendFirstImg, setSendFirstImg] = useRecoilState(SendFirstImgState);
  const [sendDetailImgs, setSendDetailImgs] = useRecoilState(SendDetailImgsState);
  const setFirstImg = useSetRecoilState(FirstImgState);
  const setDetailImgs = useSetRecoilState(DetailImgsState);
  const setPageTitle = useSetRecoilState(pageTitleState);
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const storeId = searchParams.get('store_id');
  const accessToken = sessionStorage.getItem('Authorization');

  const formChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    // 스위치문으로 추천
    const name = e.target.name;
    const value = e.target.value;
    switch (name) {
      case 'storeName':
        return setForm({ ...form, storeName: value });
      case 'body':
        return setForm({ ...form, body: value });
      case 'kakao':
        const kakaoVerify = value.replace(/[^a-zA-Z0-9]/g, '');
        return setForm({ ...form, kakao: kakaoVerify });
      case 'contact':
        const phoneNumberPattern = /(\d{3})(\d{3,4})(\d{4})/;
        let contactVerify = value.replace(/[^0-9]/g, '');
        if (contactVerify.length >= 11) {
          contactVerify = contactVerify.replace(phoneNumberPattern, '$1-$2-$3');
        }
        if (contactVerify.length >= 14) {
          return;
        }
        return setForm({ ...form, contact: contactVerify });
      case 'category':
        return setForm({ ...form, category: value });
    }
  };

  const storeAddPost = async () => {
    const inputVerify = formVerify(form);
    if (!inputVerify) {
      return;
    }
    if (!sendFirstImg) {
      return alert('대표사진을 등록해주세요.');
    }
    if (sendDetailImgs.length < 3) {
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
          Authorization: accessToken,
        },
        body: JSON.stringify(form),
      });
      if (!res.ok) {
        setIsLoading((prev) => !prev);
        return alert('업체 등록에 실패했습니다.');
      }
      const json = await res.json();
      const imgRes = await fetch(`${API_URL}/storeImages/${json.storeId}`, {
        method: 'POST',
        headers: {
          Authorization: accessToken,
        },
        body: imgForm,
      });
      if (!imgRes.ok) {
        setIsLoading((prev) => !prev);
        return alert('업체 이미지 등록에 실패했습니다.');
      }
      setIsLoading((prev) => !prev);
      window.location.href = `/category/${json.storeId}`;
    } catch (error) {
      console.error(error);
    }
  };

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
        items: json.items,
      });
      setFirstImg(json.storeImages[0]);
      setDetailImgs(json.storeImages.slice(1));
      setFetchImgsCount(json.storeImages.slice(1).length);
    } catch (error) {
      console.error(error);
    }
  };

  const storeEditPatch = async (storeId: string) => {
    const inputVerify = formVerify(form);
    if (!inputVerify) {
      return;
    }
    const imgForm = new FormData();
    sendDetailImgs.forEach((img) => imgForm.append(`images`, img));
    imgForm.append('thumbnailImage', sendFirstImg);
    try {
      setIsLoading((prev) => !prev);
      const res = await fetch(`${API_URL}/stores/${storeId}`, {
        // 동기 필요없음
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          Authorization: accessToken,
        },
        body: JSON.stringify(form),
      });
      if (!res.ok) {
        setIsLoading((prev) => !prev);
        return alert('업체 등록에 실패했습니다.');
      }
      const imgRes = await fetch(`${API_URL}/storeImages/${storeId}`, {
        method: 'PATCH',
        headers: {
          Authorization: accessToken,
        },
        body: imgForm,
      });
      if (!imgRes.ok) {
        setIsLoading((prev) => !prev);
        return alert('업체 등록에 실패했습니다.');
      }
      setIsLoading((prev) => !prev);
      window.location.href = `/category/${storeId}`;
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    setForm({
      storeName: '',
      body: '',
      address: '',
      kakao: '',
      contact: '',
      category: '',
      items: [],
    });
    setFirstImg(null);
    setDetailImgs([]);
    setSendFirstImg(null);
    setSendDetailImgs([]);
    const path = location.pathname.substring(6);
    if (path === '/edit') {
      setPageTitle('업체 수정하기');
      setBtnText((prev) => !prev);
      storeEditFetch(storeId);
    }
  }, []);

  return (
    <StoreAddSection>
      {isLoading ? (
        <div className="text-center text-2xl font-bold">
          업체 등록/수정 중입니다...
        </div>
      ) : (
        <>
          <StoreAddTop formChangeHandler={formChangeHandler} />
          <AddProduct />
          <AddImages fetchImgsCount={fetchImgsCount} />
          <AddBtn
            type="button"
            onClick={() => {
              if (btnText) {
                storeAddPost();
              } else {
                storeEditPatch(storeId);
              }
            }}
          >
            {btnText ? '등록하기' : '수정하기'}
          </AddBtn>
        </>
      )}
    </StoreAddSection>
  );
}

export default StoreAdd;

const formVerify = (form: StoreAddFormType) => {
  if (!form.storeName) {
    alert('업체명을 입력해주세요.');
    return false;
  } else if (!form.body) {
    alert('소개글을 입력해주세요.');
    return false;
  } else if (!form.address) {
    alert('주소를 선택해주세요.');
    return false;
  } else if (!form.kakao) {
    alert('카카오톡 ID를 입력해주세요.');
    return false;
  } else if (!form.contact || form.contact.length !== 13) {
    alert('전화번호를 입력해주세요.');
    return false;
  } else if (!form.category) {
    alert('카테고리를 선택해주세요.');
    return false;
  } else if (!form.items.length) {
    alert('상품을 1개 이상 등록해주세요.');
    return false;
  }
  return true;
};
