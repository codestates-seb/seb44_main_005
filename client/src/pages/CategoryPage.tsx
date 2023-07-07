import React from 'react';
import CategoryCard from '../components/Categorybar/CategoryCard';
import { recommend } from '../dummy/main';
import tw from 'tailwind-styled-components';
import { Link } from 'react-router-dom';

function CategoryPage() {
  return (
    <Style>
      <CategoryContainer>
        <span className="font-semibold text-2xl">
          전체상품 {recommend.recommend.length}
        </span>
        <Option>
          <Link to="/category?category_name=[value]&sort=recommend">
            • 관심순
          </Link>
          <Link to="/category?category_name=[value]&sort=high_rate">
            • 높은 평점순
          </Link>
          <Link to="/category?category_name=[value]&sort=low_price">
            • 낮은 가격순
          </Link>
          <Link to="/category?category_name=[value]&sort=high_price">
            • 높은 가격순
          </Link>
        </Option>
      </CategoryContainer>
      <Category>
        {recommend.recommend.map((el) => {
          return <CategoryCard data={el} key={el.store_id} />;
        })}
      </Category>
    </Style>
  );
}

const Style = tw.div`
  mx-[70px]
  flex
  flex-col
`;
const CategoryContainer = tw.section`
  flex
  justify-between
  items-start
  justify-evenly
`;

const Option = tw.div`
  flex
  w-[435px]
  mt-[15px]
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
