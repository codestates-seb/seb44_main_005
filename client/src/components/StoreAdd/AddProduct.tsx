import { useState } from "react";
import { useSetRecoilState } from "recoil";

import {
  Input,
  InputTitle,
  ProductAddBtn,
  ProductBox,
  ProductInputTitle,
  Ticket
} from "../../styles/StoreAdd/StoreAdd";
import { formState } from "../../pages/StoreAdd";

function AddProduct() {
  const [products, setProducts] = useState([]);
  const [product, setProduct] = useState({});
  const setForm = useSetRecoilState(formState);

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
    setForm((prev) => ({...prev, items: products}));
  }

  return (
    <div>
      <div className="flex mb-6">
        <InputTitle>상품등록</InputTitle>
        <ProductBox> 
          <div className="flex mb-6 items-center">
            <ProductInputTitle>상품명</ProductInputTitle>
            <Input type="text" name="itemName" onChange={productChangeHandler} />
          </div>
          <div className="flex mb-6 items-center">
            <ProductInputTitle>가격</ProductInputTitle>
            <Input type="text" name="price" onChange={productChangeHandler} />
          </div>
          <div className="flex mb-6 items-center">
            <ProductInputTitle>티켓 개수</ProductInputTitle>
            <Input type="text" name="totalTicket" onChange={productChangeHandler} />
          </div>
          <ProductAddBtn type="button" onClick={productAddHandler}>등록하기</ProductAddBtn>
        </ProductBox>
      </div>
      {products.map((el, idx) => (
        <Ticket key={idx}>{el.itemName} {el.price.toLocaleString('ko-KR')} X {el.totalTicket}</Ticket>
      ))}
    </div>
  );
}

export default AddProduct;