import { Link, useSearchParams } from 'react-router-dom';
import { useEffect, useState } from 'react';

import CategoryCard from '../components/Categorybar/CategoryCard';
import {
  Style,
  CategoryContainer,
  Option,
  Category,
} from '../styles/Category/CategoryPage';

function CategoryPage() {
  const url = import.meta.env.VITE_APP_API_URL;
  const [searchParams] = useSearchParams();

  const categoryName = searchParams.get('category_name');
  const sort = searchParams.get('sort');
  const [categoryData, setCategoryData] = useState({
    pageInfo: [{ storeCount: 0 }],
    data: [],
  });

  useEffect(() => {
    const fetchData = async () => {
      const res = await fetch(
        `${url}/stores?category=${categoryName}&sort_field=${sort}`
      );
      const data = await res.json();
      //에러처리
      if (res.status !== 200) throw res;
      setCategoryData(data);
      console.log(data);
    };

    fetchData();
  }, [categoryName, sort]);

  return (
    <Style>
      <CategoryContainer>
        <span className="font-semibold text-2xl">
          전체상품 {categoryData.pageInfo[0].storeCount}
        </span>
        <Option>
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
        </Option>
      </CategoryContainer>
      <Category>
        {categoryData.data.map((el) => {
          return <CategoryCard data={el} key={el.storeId} />;
        })}
      </Category>
    </Style>
  );
}

export default CategoryPage;
