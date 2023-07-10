import { Link, useSearchParams } from 'react-router-dom';
import tw from 'tailwind-styled-components';

import CategoryCard from '../components/Categorybar/CategoryCard';
import { category } from '../dummy/category';

function CategoryPage() {
  const [searchParams] = useSearchParams();
  const categoryName = searchParams.get('category_name');
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

const Style = tw.div`
  flex
  flex-col
`;
const CategoryContainer = tw.section`
  flex
  justify-between
  items-start
  justify-evenly
  my-[20px]
  mx-[100px]
`;

const Option = tw.div`
  flex
  w-[435px]
  mt-[10px]
  justify-around
  text-sm
`;

const Category = tw.div`
  flex
  flex-col
  items-center
  relative
`;

export default CategoryPage;
