import { Link, useSearchParams } from 'react-router-dom';
import { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { CategoryData } from '../store/categoryAtom';

import CategoryCard from '../components/Categorybar/CategoryCard';
import {
  Style,
  CategoryContainer,
  Option,
  Category,
} from '../styles/Category/CategoryPage';

function CategoryPage() {
  const [searchParams] = useSearchParams();
  const categoryName = searchParams.get('category_name');
  const sort = searchParams.get('sort');

  const [category, setCategory] = useRecoilState(CategoryData);

  const storesFetch = async () => {
    const res = await fetch(
      `/stores?category=${categoryName}&sort_field=${sort}`
    );
    const data = await res.json();
    setCategory(data);
  };

  useEffect(() => {
    storesFetch();
  }, [categoryName, sort]);

  return (
    <Style>
      <CategoryContainer>
        <span className="font-semibold text-2xl">
          전체상품 {category.pageInfo[0].storeCount}
        </span>
        <Option>
          <Link
            to={`/category?category_name=${categoryName}&sort=recommend`}
            className="mr-[25px]"
          >
            • 관심순
          </Link>
          <Link
            to={`/category?category_name=${categoryName}&sort=high_rate`}
            className="mr-[25px]"
          >
            • 높은 평점순
          </Link>
          <Link
            to={`/category?category_name=${categoryName}&sort=low_price`}
            className="mr-[25px]"
          >
            • 낮은 가격순
          </Link>
          <Link
            to={`/category?category_name=${categoryName}&sort=high_price`}
            className="mr-[25px]"
          >
            • 높은 가격순
          </Link>
          <Link
            to={`/category?category_name=${categoryName}&sort=high_price`}
            className="mr-[25px]"
          >
            • 리뷰 많은순
          </Link>
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
