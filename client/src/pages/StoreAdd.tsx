import { useState } from "react";

import {
  AddBtn,
  Input,
  InputTitle,
  IntroContent,
  StoreAddSection,
  Ticket,
} from "../styles/StoreAdd/StoreAdd";
import Address from "../components/StoreAdd/Address";
import SelectCategory from "../components/StoreAdd/SelectCategory";
import AddProduct from "../components/StoreAdd/AddProduct";
import AddImages from "../components/StoreAdd/AddImages";

function StoreAdd() {
  const [products, setProducts] = useState([]);
  const [product, setProduct] = useState({});

  const productChangeHandler = (e) => {
    if (e.target.name === "itemName") {
      setProduct({...product, itemName: e.target.value});
    }
    else if (e.target.name === "price") {
      setProduct({...product, price: Number(e.target.value)});
    }
    else if (e.target.name === "totalTicket") {
      setProduct({...product, totalTicket: Number(e.target.value)});
    }
  }

  const productAddHandler = () => {
    setProducts([...products, {...product}]);
  }

  return (
    <StoreAddSection>
      <div className="text-2xl font-semibold mb-10 ml-10">업체등록하기</div>
      <div className="flex mb-6 items-center">
        <InputTitle>업체명</InputTitle>
        <Input type="text" />
      </div>
      <div className="flex mb-6">
        <InputTitle className="pt-3">소개글</InputTitle>
        <IntroContent />
      </div>
      <Address />
      <div className="flex mb-6 items-center">
        <InputTitle>카카오톡 ID</InputTitle>
        <Input type="text" />
      </div>
      <div className="flex mb-6 items-center">
        <InputTitle>업체 전화번호</InputTitle>
        <Input type="text" />
      </div>
      <SelectCategory />
      <AddProduct
        productChangeHandler={productChangeHandler}
        productAddHandler={productAddHandler}
      />
      {products.map((el) => (
        <Ticket>{el.itemName} {el.price.toLocaleString('ko-KR')} X {el.totalTicket}</Ticket>
      ))}
      <AddImages />
      <AddBtn type="button">등록하기</AddBtn>
    </StoreAddSection>
  );
}

export default StoreAdd;
