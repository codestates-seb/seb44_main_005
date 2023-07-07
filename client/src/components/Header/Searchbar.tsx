import React from 'react';
import search from '../../assets/search.svg';
import {
  SearchbarContainer,
  SearchbarInput,
} from '../../styles/Header/Searchbar';

function Searchbar() {
  return (
    <SearchbarContainer>
      <SearchbarInput
        type="text"
        id="keyword"
        placeholder="상품을 검색해보세요"
      />
      <button type="button">
        <img
          src={search}
          className="ml-2 w-[30px] absolute top-[25px] left-[610px]"
        />
      </button>
    </SearchbarContainer>
  );
}

export default Searchbar;
