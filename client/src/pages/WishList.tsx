import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import search from '../assets/search.svg';
import starOne from '../assets/starOne.svg';
import { PiHeartFill } from 'react-icons/pi';
import {
  NoWishList,
  NoWishImgSize,
  NoWishTitle,
  WishContainer,
  WishCountTitle,
} from '../styles/MyPage/WishList';

function WishList() {
  const APIURL = import.meta.env.VITE_APP_API_URL;
  const [wishlist, setWishList] = useState([]);
  const navigate = useNavigate();

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
    }
  };

  const handleTitleClick = (storeId) => {
    navigate(`/category/${storeId}`);
  }
  
  return (
    <WishContainer>
      {wishlist.length === 0 ? (
        <NoWishList>
          <NoWishImgSize src={search} alt="search" />
          <NoWishTitle>아직 담긴 위시리스트가 없네요!</NoWishTitle>
          <p>관심가는 상품을 찾아 ♡를 눌러 위시리스트에 차곡차곡 쌓아볼까요?</p>
        </NoWishList>
      ) : (
        <div className='flex flex-col space-y-5'>
          <div>
            <WishCountTitle>
              위시 상품 {wishlist.length}개
            </WishCountTitle>
          </div>
          {wishlist.map((item) => (
            <div key={item.storeId} className='border-[1px] border-[#4771B7] w-[800px] h-[200px]'>
              <div className='flex flex-row'>
                <div>
                  <img className='h-[198px] w-[250px] object-cover' src={item.img} alt="temporary image" />
                </div>
                <div className='p-4 w-[550px] flex flex-col justify-between'>
                  <div>
                    <p 
                      className='text-[20px] font-semibold pb-[2px] cursor-pointer'
                      onClick={() => handleTitleClick(item.storeId)}
                    >{item.title}</p>
                    <div className='flex flex-row space-x-1'>
                      <img src={starOne} alt='star' />
                      <span className='text-[15px] font-medium'>{item.rating}</span>
                      <span className='text-[15px] font-medium'>({item.reviewCount})</span>
                      <span className='text-[15px] text-[#707070] font-medium'>{item.address}</span>
                    </div>
                  </div>
                  <div className='flex flex-row justify-between'>
                    <span className='text-[15px] font-semibold'>{item.price}원~</span>
                    <PiHeartFill size="24" color="#4771B7" />
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </WishContainer>
  );
}

export default WishList;
