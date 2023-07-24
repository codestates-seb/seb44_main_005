import { useRecoilState } from "recoil";

import {
  Input,
  InputTitle,
  ProductAddBtn,
  ProductBox,
  ProductInputTitle,
  Ticket,
  TicketDeleteBtn
} from "../../styles/StoreAdd/StoreAdd";
import { ProductsState, StoreformState } from "../../store/storeAddAtom";

function AddProduct() {
  const [product, setProduct] = useRecoilState(ProductsState);
  const [form, setForm] = useRecoilState(StoreformState);

  const productChangeHandler = (e) => {
    const value = e.target.value;
    const onlyNumbers = Number(value.replace(/[^0-9]/g, ""));
    if (e.target.name === "itemName") {
      setProduct({...product, itemName: value});
    }
    else if (e.target.name === "price") {
      setProduct({...product, price: onlyNumbers});
    }
    else if (e.target.name === "totalTicket") {
      setProduct({...product, totalTicket: onlyNumbers});
    }
  }

  const productAddHandler = () => {
    if (product.itemName === '' || !product.price || !product.totalTicket) {
      return alert('상품 정보들을 제대로 기입해주세요.');
    }
    setForm((prev) => ({...prev, items: [...prev.items, {...product}]}));
    setProduct({
      itemName: '',
      price: 0,
      totalTicket: 0
    })
  }

  const productDeleteHandler = (idx) => {
    const result = form.items.filter((_, itemIdx) => itemIdx !== idx);
    setForm((prev) => ({...prev, items: [...result]}));
  }

  return (
    <div>
      <div className="flex mb-6">
        <InputTitle>상품등록</InputTitle>
        <ProductBox> 
          <div className="flex mb-6 items-center">
            <ProductInputTitle>상품명</ProductInputTitle>
            <Input
              type="text"
              name="itemName"
              value={product.itemName}
              onChange={productChangeHandler}
            />
          </div>
          <div className="flex mb-6 items-center">
            <ProductInputTitle>가격</ProductInputTitle>
            <Input
              type="text"
              name="price"
              value={product.price}
              onChange={productChangeHandler}
            />
          </div>
          <div className="flex mb-6 items-center">
            <ProductInputTitle>티켓 개수</ProductInputTitle>
            <Input
              type="text"
              name="totalTicket"
              value={product.totalTicket}
              onChange={productChangeHandler}
            />
          </div>
          <ProductAddBtn type="button" onClick={productAddHandler}>등록하기</ProductAddBtn>
        </ProductBox>
      </div>
      {form.items && form.items.map((el, idx) => (
        <Ticket key={idx}>
          <div>{el.itemName} {el.price.toLocaleString('ko-KR')} X {el.totalTicket}</div>
          <TicketDeleteBtn onClick={() => productDeleteHandler(idx)}>지우기</TicketDeleteBtn>
        </Ticket>
      ))}
    </div>
  );
}

export default AddProduct;