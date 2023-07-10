import {
  Input,
  InputTitle,
  ProductAddBtn,
  ProductBox,
  ProductInputTitle
} from "../../styles/StoreAdd/StoreAdd";

function AddProduct({ productChangeHandler, productAddHandler }) {
  return (
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
  );
}

export default AddProduct;