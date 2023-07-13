import { useRecoilState, useSetRecoilState } from 'recoil';
import { useNavigate } from 'react-router-dom';

import { searchKeyword } from '../../store/searchbarAtom';
import search from '../../assets/search.svg';
import { categoryData } from '../../store/categoryAtom';
import {
  SearchbarContainer,
  SearchbarInput,
} from '../../styles/Header/Searchbar';

function Searchbar() {
  const url = import.meta.env.VITE_APP_API_URL;
  const [keyword, setKeyword] = useRecoilState<string>(searchKeyword);
  const setCategory = useSetRecoilState(categoryData);
  const navigate = useNavigate();

  const searchFetch = async () => {
    try {
      const res = await fetch(`${url}/search?keyword=${keyword}`);
      const data = await res.json();
      // 에러처리
      if (res.status !== 200) throw res;
      setCategory(data);

      navigate(
        `/category?category_name=all&sort=likeCount/search?keyword=${keyword}`
      );
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <SearchbarContainer>
      <SearchbarInput
        type="text"
        id="keyword"
        value={keyword}
        onChange={(e) => setKeyword(e.currentTarget.value)}
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
