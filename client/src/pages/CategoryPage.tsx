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
          <Link to={`/category?category_name=${categoryName}&sort=recommend`}>
            • 관심순
          </Link>
          <Link to={`/category?category_name=${categoryName}&sort=high_rate`}>
            • 높은 평점순
          </Link>
          <Link to={`/category?category_name=${categoryName}&sort=low_price`}>
            • 낮은 가격순
          </Link>
          <Link to={`/category?category_name=${categoryName}&sort=high_price`}>
            • 높은 가격순
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
