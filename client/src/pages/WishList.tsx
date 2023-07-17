import {
  WishContainer,
} from '../styles/MyPage/WishList';

function WishList() {
  const exam = "https://www.pinterest.co.kr/pin/15973773672121528/"
  
  return(
    <WishContainer>
      <p className='text-[24px] font-semibold'>위시상품 0개</p>
      <div className='flex flex-row border-[1.5px] border-[#4771B7] w-[700px] h-[200px]'>
        <div className='relative w-[250px] h-[200px]'>
          <div className='absolute inset-0 w-[250px] h-[200px]'>
            <img className='w-full h-full object-cover' src={exam} alt='example' />
          </div>
        </div>
        <div>
          <p>컨텐츠 자리</p>
        </div>
      </div>
    </WishContainer>
  );
}

export default WishList;
