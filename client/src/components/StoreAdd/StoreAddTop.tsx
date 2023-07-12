import Address from "./Address";
import SelectCategory from "./SelectCategory";
import { InputTitle, Input, IntroContent } from "../../styles/StoreAdd/StoreAdd";
import { useRecoilValue } from "recoil";

import { pageTitleState } from "../../pages/StoreAdd";

function StoreAddTop({ formChangeHandler }) {
  const pageTitle = useRecoilValue(pageTitleState);
  return (
    <>
      <div className="text-2xl font-semibold mb-10 ml-10">{pageTitle}</div>
      <div className="flex mb-6 items-center">
        <InputTitle>업체명</InputTitle>
        <Input onChange={formChangeHandler} type="text" name="storeName" />
      </div>
      <div className="flex mb-6">
        <InputTitle className="pt-3">소개글</InputTitle>
        <IntroContent onChange={formChangeHandler} name="content" />
      </div>
      <Address />
      <div className="flex mb-6 items-center">
        <InputTitle>카카오톡 ID</InputTitle>
        <Input onChange={formChangeHandler} type="text" name="kakao" />
      </div>
      <div className="flex mb-6 items-center">
        <InputTitle>업체 전화번호</InputTitle>
        <Input onChange={formChangeHandler} type="text" name="contact" />
      </div>
      <SelectCategory formChangeHandler={formChangeHandler} />
    </>
  );
}

export default StoreAddTop;