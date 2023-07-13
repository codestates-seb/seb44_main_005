import { Link, useSearchParams } from 'react-router-dom';
import { useEffect, useState } from 'react';

import CategoryCard from '../components/Categorybar/CategoryCard';
import {
  Style,
  CategoryContainer,
  Option,
  Category,
} from '../styles/Category/CategoryPage';

import { useRecoilState } from 'recoil';
import { categoryData } from '../store/categoryAtom';
import { searchKeyword } from '../store/searchbarAtom';

function CategoryPage() {
  const url = import.meta.env.VITE_APP_API_URL;
  const [searchParams] = useSearchParams();
  const [isSearch, setIsSearch] = useState(false);
  const [keyword] = useRecoilState(searchKeyword);

  const categoryName = searchParams.get('category_name');
  const sort = searchParams.get('sort');

  const [category, setCategory] = useRecoilState(categoryData);
  const isSearchResult = !!keyword;

  useEffect(() => {
    const fetchData = async () => {
      let data;
      //검색조건이 있을 때
      if (isSearchResult) {
        setIsSearch(true);
        const res = await fetch(`${url}/search?keyword=${keyword}`);
        data = await res.json();
        if (res.status !== 200) throw res;
      } else {
        // 검색조건이 없을 때
        const res = await fetch(
          `${url}/stores?category=${categoryName}&sort_field=${sort}`
        );

        data = await res.json();
        setIsSearch(false);
        //에러처리
        if (res.status !== 200) throw res;
      }
      setCategory(data);
    };
    fetchData();
  }, [categoryName, sort]);

  return (
    <Style>
      <CategoryContainer>
        <span className="font-semibold text-2xl">
          전체상품 {category.pageInfo[0].storeCount}
        </span>
        <Option>
          {!isSearch && (
            <>
              <Link
                to={`/category?category_name=${categoryName}&sort=likeCount`}
                className="mr-[25px]"
              >
                • 관심순
              </Link>
              <Link
                to={`/category?category_name=${categoryName}&sort=rating`}
                className="mr-[25px]"
              >
                • 높은 평점순
              </Link>
              <Link
                to={`/category?category_name=${categoryName}&sort=lowPrice`}
                className="mr-[25px]"
              >
                • 낮은 가격순
              </Link>
              <Link
                to={`/category?category_name=${categoryName}&sort=highPrice`}
                className="mr-[25px]"
              >
                • 높은 가격순
              </Link>
              <Link
                to={`/category?category_name=${categoryName}&sort=reviewCount`}
                className="mr-[25px]"
              >
                • 리뷰 많은순
              </Link>
            </>
          )}
        </Option>
      </CategoryContainer>
      <Category>
        {category.data.map((el) => {
          return <CategoryCard data={el} key={el.storeId} />;
        })}
      </Category>
    </Style>
  );
}

export default CategoryPage;
