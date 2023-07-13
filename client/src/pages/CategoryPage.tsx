import { Link, useSearchParams } from 'react-router-dom';
import { useEffect, useState } from 'react';

import CategoryCard from '../components/Categorybar/CategoryCard';
import {
  Style,
  CategoryContainer,
  Option,
  Category,
} from '../styles/Category/CategoryPage';
import { useRecoilState, useRecoilValue } from 'recoil';
import { categoryData } from '../store/categoryAtom';
import { search, searchKeyword } from '../store/searchbarAtom';
import Loading from '../components/Loading/Loading';

function CategoryPage() {
  const url = import.meta.env.VITE_APP_API_URL;
  const [searchParams] = useSearchParams();
  const [isLoading, setIsLoading] = useState(false);
  const categoryName = searchParams.get('category_name');
  const sort = searchParams.get('sort');

  // 전역 상태 변수
  const keyword = useRecoilValue(searchKeyword);
  const [isSearch, setIsSearch] = useRecoilState(search);
  const [category, setCategory] = useRecoilState(categoryData);

  useEffect(() => {
    const fetchData = async () => {
      let data;
      // 검색 조건이 있을 때
      if (keyword) {
        setIsSearch(true);
        setIsLoading(true);
        const res = await fetch(`${url}/search?keyword=${keyword}`);
        data = await res.json();
        if (res.status !== 200) throw res;
      } else {
        // 검색 조건이 없을 때
        const res = await fetch(
          `${url}/stores?category=${categoryName}&sort_field=${sort}`
        );
        data = await res.json();
        setIsSearch(false);
        // 에러 처리
        if (res.status !== 200) throw res;
      }
      // 2초 동안 로딩 표시

      setTimeout(() => {
        setIsLoading(false);
      }, 500);
      setCategory(data);
    };

    fetchData();
  }, [categoryName, sort]);

  return (
    <Style>
      <CategoryContainer style={{ display: isLoading ? 'none' : 'flex' }}>
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
      {isLoading ? (
        <Loading />
      ) : (
        <Category>
          {category.data.map((el) => (
            <CategoryCard data={el} key={el.storeId} />
          ))}
        </Category>
      )}
    </Style>
  );
}

export default CategoryPage;
