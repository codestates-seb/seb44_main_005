import search from '../../assets/search.svg';
import { useSearchParams } from 'react-router-dom';

function NoResult() {
  const [searchParams] = useSearchParams();
  const keywords = searchParams.get('keyword');
  return (
    <div className="mx-0 my-auto w-[1060px] h-[500px] relative  ml-[300px] mt-[30px]">
      <div className="pt-[40px] pb-[120px] px-0 relative">
        <div className="flex mt-[100px] justify-center items-center">
          <div className="flex flex-col text-center items-center">
            <img
              src={search}
              className="w-[100px] h-[100px] mb-[30px] mr-[5px]"
            />
            <div className="ml-[15px]">
              <p className="mb-[3px]">
                <span className="text-[#4771B7] font-medium">{keywords}</span>{' '}
                의 검색결과가 없습니다.
              </p>
              <p>지역/업체명을 검색해보세요.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default NoResult;
