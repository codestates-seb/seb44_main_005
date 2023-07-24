//wishlist
import { useEffect, useState } from 'react';
import CategoryCard from '../components/Categorybar/CategoryCard'; 
import NothingComponent from '../components/MyPage/NothingComponent';
import LoadingComponent from '../components/Loading/LoadingComponent';
import search from '../assets/search.svg';
import {
  NoWishList,
  WishContainer,
  WishCountTitle,
  NoWishImgSize,
} from '../styles/MyPage/WishList';

function WishList() {
  const APIURL = import.meta.env.VITE_APP_API_URL;
  const [wishlist, setWishList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchWishList();
  }, []);

  const fetchWishList = async () => {
    try {
      const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const res = await fetch(`${APIURL}/mypage/wishlist`, {
        method: 'GET',
        headers: {
          'Authorization': ACCESS_TOKEN
        }
      });

      if (res.ok) {
        const data = await res.json();
        setWishList(data.stores);
      } else {
        console.error('위시리스트 불러오기에 실패했습니다.', res.status);
      }
    } catch (error) {
      console.error('에러가 발생했습니다.', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <WishContainer>
      {loading ? (
        <div className='flex flex-col justify-center items-center h-[800px] w-[902px]'>
          <LoadingComponent />
        </div>
      ) : wishlist.length === 0 ? (
        <NoWishList>
          <NoWishImgSize src={search} alt="nothingimg" />
          <NothingComponent 
            title='아직 담긴 위시리스트가 없네요!'
            description='관심가는 상품을 찾아 ♡를 눌러 위시리스트에 차곡차곡 쌓아볼까요?'
          />
        </NoWishList>
      ) : (
        <div className='flex flex-col space-y-5 h-[100%]'>
          <div>
            <WishCountTitle>
              위시 상품 {wishlist.length}개
            </WishCountTitle>
          </div>
          {wishlist
            .filter((item) => item.isLike) // Filter out items with isLike: false
            .map((item) => (
              <CategoryCard key={item.storeId} data={item} />
            ))}
        </div>
      )}
    </WishContainer>
  );
}

export default WishList;
