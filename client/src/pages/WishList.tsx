import SideBar from '../components/MyPage/SideBar';
import { wishlist } from '../dummy/wishlist';
import search from '../assets/search.svg';
import starOne from '../assets/starOne.svg';
import { PiHeartFill } from 'react-icons/pi';
import { 
  MyPageContainer,
  WishContainer,
  WishCount,
  EachWishCard,
  CardImgSize,
  NoWishList,
  NoWishImgSize,
  NoWishTitle
} from '../styles/MyPage/MyPage';

function WishList() {
  if (wishlist.data.length === 0) {
    return (
      <MyPageContainer>
        <SideBar />
        <NoWishList>
          <NoWishImgSize src={search} alt="search" />
          <NoWishTitle>
            아직 담긴 위시리스트가 없네요!
          </NoWishTitle>
          <p>관심가는 상품을 찾아 ♡를 눌러 위시리스트에 차곡차곡 쌓아볼까요?</p>
        </NoWishList>
      </MyPageContainer>
    );
  }

  return (
    <MyPageContainer>
      <WishContainer>
        <WishCount>
          <p>위시 상품 {wishlist.data.length}개</p>
        </WishCount>
        {wishlist.data.map((item) => (
          <div key={item.storeId}>
            <EachWishCard>
              <CardImgSize src={item.storeImage} alt={item.storeName} />
              <div className="px-6 py-4 w-[100%] h-[200px]">
                <div className="font-semibold text-lg mb-2">{item.storeName}</div>
                <div className='border-[1px] border-red-500 w-[100%] grid gap-20 content-between'>
                  <div className='flex flex-row space-x-4'>
                    <div className='flex flex-row space-x-[2px]'>
                      <img src={starOne} alt="star" />
                      <div>{item.rating}</div>
                      <div>({item.reviewCount})</div>
                    </div>
                    <div>{item.address}</div>
                  </div>
                  <div className='flex flex-row border-[1px] border-black justify-between'>
                    <p>20,000원~</p>
                    <PiHeartFill alt='heart' size="24" color="#4771B7" />
                  </div>
                </div>
              </div>
            </EachWishCard>
          </div>
        ))}
      </WishContainer>
    </MyPageContainer>
  );
}

export default WishList;
