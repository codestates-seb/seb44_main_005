import { useRecoilState } from 'recoil';
import { useNavigate } from 'react-router-dom';
import { useEffect, useRef, useState } from 'react';

import { searchKeyword } from '../../store/searchbarAtom';
import search from '../../assets/search.svg';
import {
  SearchbarContainer,
  SearchbarInput,
  AutoSearchContainer,
  AutoSearchData,
  SearchIcon,
} from '../../styles/Header/Searchbar';

interface AutoData {
  storeId: number;
  category: string;
  title: string;
  content: string;
  address: string;
  rating: number;
  reviewCount: string;
  price: number;
  isLike: boolean;
  img: string;
}

function Searchbar() {
  const [keyword, setKeyword] = useRecoilState<string>(searchKeyword);
  const [keyItems, setKeyItems] = useState<AutoData[]>([]);
  const [open, setOpen] = useState(false);
  const inputRef = useRef(null);
  const navigate = useNavigate();
  const url = import.meta.env.VITE_APP_API_URL;

  const autoData = async () => {
    try {
      const res = await fetch(`${url}/search?keyword=${keyword}`, {
        headers: { Authorization: sessionStorage.getItem('Authorization') },
      });
      if (!res.ok) {
        throw new Error('Failed to fetch data');
      }
      const data = await res.json();
      setKeyItems(data.data);
    } catch (error) {
      console.error(error);
    }
  };

  const searchFetch = async () => {
    try {
      await navigate(`/search?keyword=${keyword}`);
      setKeyword(keyword);
    } catch (error) {
      console.error(error);
    }
  };
  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      inputRef.current.blur();
      searchFetch();
      setOpen(false);
    }
  };

  useEffect(() => {
    const debounce = setTimeout(() => {
      if (keyword) autoData();
    }, 100);
    return () => {
      clearTimeout(debounce);
    };
  }, [keyword]);

  return (
    <SearchbarContainer>
      <SearchbarInput
        type="text"
        autoComplete="off"
        id="keyword"
        ref={inputRef}
        value={keyword}
        onChange={(e) => setKeyword(e.currentTarget.value)}
        onKeyUp={handleKeyDown}
        placeholder="상품을 검색해보세요"
        onFocus={() => setOpen(true)}
        onBlur={() => setOpen(false)}
      />
      <img
        src={search}
        onClick={searchFetch}
        className="absolute ml-2 w-[30px] top-[5px] left-[452px] cursor-pointer z-50"
      />
      {open && keyItems.length > 0 && (
        <AutoSearchContainer>
          <ul className="pt-[20px]">
            {keyItems.map((item) => (
              <AutoSearchData
                key={item.title}
                onClick={() => {
                  setKeyword(item.title);
                }}
              >
                <SearchIcon src={search} onClick={() => setOpen(false)} />
                <a href={`/search?keyword=${keyword}`}>{item.title}</a>
              </AutoSearchData>
            ))}
          </ul>
        </AutoSearchContainer>
      )}
    </SearchbarContainer>
  );
}

export default Searchbar;
