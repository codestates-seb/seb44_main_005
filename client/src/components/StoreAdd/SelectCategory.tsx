import { CategorySelect, InputTitle } from "../../styles/StoreAdd/StoreAdd";

function SelectCategory() {
  return (
    <div className="flex mb-6 items-center">
      <InputTitle>카테고리</InputTitle>
      <CategorySelect>
        <option>선택해주세요</option>
        <option>스노클링/다이빙</option>
        <option>수상레저</option>
        <option>서핑</option>
        <option>승마</option>
        <option>ATV</option>
      </CategorySelect>
    </div>
  );
}

export default SelectCategory;