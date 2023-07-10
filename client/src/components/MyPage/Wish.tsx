import search from '../../assets/search.svg';

function Wish() {
    return (
        <div className='flex flex-col justify-center items-center space-y-5'>
            <img className='w-[100px]' src={search} alt='search' />
            <p className='text-2xl font-semibold pt-12'>아직 담긴 위시리스트가 없네요!</p>
            <p>관심가는 상품을 찾아 ♡를 눌러 위시리스트에 차곡차곡 쌓아볼까요?</p>
        </div>
    );
};

export default Wish;