import search from '../../assets/search.svg';
import { recommend } from '../../dummy/main';

function Wish() {
    if (recommend.recommend.length === 0) {
        return (
            <div className='flex flex-col justify-center items-center space-y-5'>
                <img className='w-[100px]' src={search} alt='search' />
                <p className='text-2xl font-semibold pt-12'>아직 담긴 위시리스트가 없네요!</p>
                <p>관심가는 상품을 찾아 ♡를 눌러 위시리스트에 차곡차곡 쌓아볼까요?</p>
            </div>
        );
    }

    return (
        <div className="flex flex-col justify-center items-center space-y-5">
            {recommend.recommend.map((item) => (
                <div key={item.store_id} className="flex flex-row rounded border-[1px] border-[#4771B7]/[.44] w-[700px]">
                    <img src={item.img} alt={item.store_name} className="w-[250px] h-[200px]" />
                    <div className="px-6 py-4 w-[100%] h-[100%]">
                        <div className="font-semibold text-xl mb-2">{item.store_name}</div>
                        <div className='border-[1px] border-black'>
                            <div className='text-sm space-x-2'>
                                <span>별점</span>
                                <span>주소입니다요</span>
                            </div>
                            <div className='flex justify-between text-sm font-semibold'>
                                <span>20,000원~</span>
                                <span>하트</span>
                            </div>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default Wish;