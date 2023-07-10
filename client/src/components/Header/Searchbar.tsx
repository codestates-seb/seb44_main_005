import search from '../../assets/search.svg';
import {
  SearchbarContainer,
  SearchbarInput,
} from '../../styles/Header/Searchbar';

function Searchbar() {
  return (
    <SearchbarContainer>
      <SearchbarInput type="text" placeholder="상품을 검색해보세요" />
      <img
        src={search}
        className="ml-2 w-[30px] absolute top-[5px] left-[452px]"
      />
    </SearchbarContainer>
  );
}

export default Searchbar;
