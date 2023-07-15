import React, { useState } from 'react';
import { useSetRecoilState } from 'recoil';

import { StoreformState } from '../../store/StoreAdd';
import DaumPostcode from 'react-daum-postcode';

import {
  Input,
  InputTitle,
  SearchAddress
} from '../../styles/StoreAdd/StoreAdd';

function Address() {
  const [openPostcode, setOpenPostcode] = React.useState<boolean>(false);
  const [addressInfo, setAddressInfo] = useState("");
  const setForm = useSetRecoilState(StoreformState);

  const openPostHandler = () => {
    setOpenPostcode((prev) => !prev);
  }

  const selectAddress = (address) => {
    setForm((prev) => ({...prev, address: address.address}))
    setAddressInfo(address.address);
    setOpenPostcode((prev) => !prev);
  }

  return (
    <div className="flex mb-6 items-center relative">
      <InputTitle>주소</InputTitle>
      <Input type="text" name="address" value={addressInfo} readOnly/>
      <SearchAddress type="button" onClick={openPostHandler}>주소찾기</SearchAddress>
      {openPostcode && (
        <DaumPostcode className="absolute top-12 border-[1px] border-black" onComplete={selectAddress} autoClose={false} />
      )}
    </div>
  );
}

export default Address;