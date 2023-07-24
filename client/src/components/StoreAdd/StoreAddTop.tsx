import Address from "./Address";
import SelectCategory from "./SelectCategory";
import { InputTitle, Input, IntroContent } from "../../styles/StoreAdd/StoreAdd";
import { useRecoilValue } from "recoil";

import { StoreformState, pageTitleState } from "../../store/storeAddAtom";

function StoreAddTop({ formChangeHandler }) {
  const pageTitle = useRecoilValue(pageTitleState);
  const form = useRecoilValue(StoreformState);

  return (
    <>
      <div className="text-2xl font-semibold mb-10 ml-10">{pageTitle}</div>
      <div className="flex mb-6 items-center">
        <InputTitle>업체명</InputTitle>
        <Input
          type="text"
          name="storeName"
          value={form.storeName}
          onChange={formChangeHandler}
          placeholder="업체명을 기준으로 검색에 노출됩니다."
        />
      </div>
      <div className="flex mb-6">
        <InputTitle className="pt-3">소개글</InputTitle>
        <IntroContent
          name="body"
          value={form.body}
          onChange={formChangeHandler}
        />
      </div>
      <Address />
      <div className="flex mb-6 items-center">
        <InputTitle>카카오톡 ID</InputTitle>
        <Input
          type="text"
          name="kakao"
          value={form.kakao}
          onChange={formChangeHandler}
          placeholder="영문과 숫자 조합으로 입력해주세요."
        />
      </div>
      <div className="flex mb-6 items-center">
        <InputTitle>업체 전화번호</InputTitle>
        <Input
          type="text"
          name="contact"
          value={form.contact}
          onChange={formChangeHandler}
          placeholder="'-'를 제외하고 입력해주세요"
        />
      </div>
      <SelectCategory formChangeHandler={formChangeHandler} />
    </>
  );
}

export default StoreAddTop;