import { useSearchParams } from 'react-router-dom';
import { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { CategoryData } from '../store/categoryAtom';

import CategoryCard from '../components/Categorybar/CategoryCard';
import search from '../assets/search.svg';
import { 
  NoWishList,
  NoWishImgSize,
  NoWishTitle,
} from '../styles/MyPage/WishList';

function WishList() {
  const [searchParams] = useSearchParams();
  const categoryName = searchParams.get('category_name');
  const sort = searchParams.get('sort');

  const [category, setCategory] = useRecoilState(CategoryData);

  const getLikedCards = () => {
    return category.data.filter((el) => el.isLike);
  };

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
    <div className='border-[1px] border-[#4771B7] p-10 max-h-[800px] overflow-y-auto'>
      {getLikedCards().length === 0 ? (
        <NoWishList>
          <NoWishImgSize src={search} alt="search" />
          <NoWishTitle>
            아직 담긴 위시리스트가 없네요!
          </NoWishTitle>
          <p>관심가는 상품을 찾아 ♡를 눌러 위시리스트에 차곡차곡 쌓아볼까요?</p>
        </NoWishList>
      ) : (
        <div>
          <div>
            <span className='font-semibold text-2xl'>
              위시 상품 {getLikedCards().length}개
            </span>
          </div>
          <div>
            {getLikedCards().map((el) => {
              return <CategoryCard data={el} key={el.storeId} />;
            })}
          </div>
        </div>
      )}
    </div>
  );
}

export default WishList;
