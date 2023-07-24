import React from 'react';
import { useRecoilState } from 'recoil';

import { StoreformState } from '../../store/storeAddAtom';
import DaumPostcode from 'react-daum-postcode';

import {
  Input,
  InputTitle,
  SearchAddress
} from '../../styles/StoreAdd/StoreAdd';

function Address() {
  const [openPostcode, setOpenPostcode] = React.useState<boolean>(false);
  const [form, setForm] = useRecoilState(StoreformState);

  const openPostHandler = () => {
    setOpenPostcode((prev) => !prev);
  }

  const selectAddress = (address) => {
    setForm((prev) => ({...prev, address: address.address}))
    setOpenPostcode((prev) => !prev);
  }

  return (
    <div className="flex mb-6 items-center relative">
      <InputTitle>주소</InputTitle>
      <Input
        type="text"
        name="address"
        value={form.address}
        placeholder="주소찾기 버튼을 클릭해주세요."
      readOnly/>
      <SearchAddress type="button" onClick={openPostHandler}>주소찾기</SearchAddress>
      {openPostcode && (
        <DaumPostcode className="absolute top-12 border-[1px] border-black" onComplete={selectAddress} autoClose={false} />
      )}
    </div>
  );
}

export default Address;