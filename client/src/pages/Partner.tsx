import SelectBox from '../components/Partner/SelectBox';

function Partner() {    
  return (
    <div className="flex justify-center items-center h-[80%]">
      <div className="p-10">
        <div className="flex flex-col justify-center w-[700px]">
          <p className="text-2xl font-semibold justify-start p-10">
            파트너 등록하기
          </p>
          <form className="justify-center">
                <div className="flex space-x-5 justify-end py-2">
                    <label className="">대표자 명</label>
                    <input
                        className="border-[1px] border-[#9A9A9A] rounded-md w-[78%] px-2 py-[2px]"
                        placeholder="ex.홍길동"
                        type='text'
                    />
                </div>
                <div className="flex space-x-4 justify-end py-2">
                    <label>사업자등록번호</label>
                    <div className='w-[78%] flex flex-row'>
                        <div className='pr-4 w-[100%]'>
                            <input
                                className="border-[1px] border-[#9A9A9A] rounded-md w-[100%]  px-2 py-[2px]"
                                placeholder="123-45-67890"
                                type='text'
                            />
                            <p className='pt-1 text-red-500'>사업자 등록번호는 10자리로 입력되어야 합니다.</p>
                        </div>
                        <button className="rounded-md px-4 h-7 w-28 bg-[#4771B7] text-white" type='button'>
                            중복확인
                        </button>
                    </div>
                </div>
                <div className="flex space-x-5 justify-end py-2">
                    <label className="">업체명</label>
                    <input
                        className="border-[1px] border-[#9A9A9A] rounded-md w-[78%]  px-2 py-[2px]"
                        placeholder="ex.OO레저"
                        type='text'
                    />
                </div>
                <div className="flex space-x-5 justify-end py-2">
                    <label className="">개업 일자</label>
                    <input
                        className="border-[1px] border-[#9A9A9A] rounded-md  w-[78%] px-2 py-[2px]"
                        placeholder="2023-00-00"
                        type='date'
                    />
                </div>
                <div className="flex flex-row justify-end py-2">
                    <div className="flex space-x-5 justify-end w-[55%]">
                        <label className="w-[9%]">업태</label>
                        <input
                            className="border-[1px] border-[#9A9A9A] rounded-md w-[60%]  px-2 py-[2px]"
                            type='text'
                            name='business'
                            value='스포츠 및 여가관련 서비스업'
                            readOnly
                        />
                    </div>
                    <div className="flex space-x-5 justify-end w-[45%]">
                        <label className="">업종</label>
                        <div
                        >
                           <SelectBox />
                        </div>
                    </div>
                </div>
                <div className="flex justify-center pt-4">
                    <button className="font-semibold rounded-md text-xl px-14 py-3 bg-[#4771B7] text-white" type='submit'>
                        등록하기
                    </button>
                </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Partner;
