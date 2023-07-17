import { useRecoilState } from 'recoil';
import { useNavigate } from 'react-router-dom';

import { searchKeyword } from '../../store/searchbarAtom';
import search from '../../assets/search.svg';
import {
  SearchbarContainer,
  SearchbarInput,
} from '../../styles/Header/Searchbar';

function Searchbar() {
  const [keyword, setKeyword] = useRecoilState<string>(searchKeyword);
  const navigate = useNavigate();

  const searchFetch = async () => {
    try {
      await navigate(`/search?keyword=${keyword}`);
    } catch (error) {
      console.error(error);
    }
  };
  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      searchFetch();
    }
  };

  return (
    <SearchbarContainer>
      <SearchbarInput
        type="text"
        id="keyword"
        value={keyword}
        onChange={(e) => setKeyword(e.currentTarget.value)}
        onKeyDown={handleKeyDown}
        placeholder="상품을 검색해보세요"
      />
      <img
        src={search}
        onClick={searchFetch}
        className="ml-2 w-[30px] absolute top-[5px] left-[452px] cursor-pointer"
      />
    </SearchbarContainer>
  );
}

export default Searchbar;
