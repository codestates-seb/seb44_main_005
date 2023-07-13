import { useState } from 'react';
import SelectBox from '../components/Partner/SelectBox';

function Partner() {
  const [regiNumber, setRegiNumber] = useState('');
  const [isCheckingDuplicate, setIsCheckingDuplicate] = useState(false);
  const [isDuplicateRegiNumber, setIsDuplicateRegiNumber] = useState(false);

  const handleRegiNumberChange = (e) => {
    const input = e.target.value.replace(/\D/g, '');
    const formattedInput = input.slice(0, 10);
    const formattedRegiNumber = formattedInput.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');
    setRegiNumber(formattedRegiNumber);
  };

  const isRegiNumberValid = regiNumber.length === 12;
  const isRegiNumberIncomplete = regiNumber.length > 0 && regiNumber.length <12;

  const handleDuplicateCheck = async () => {
    setIsCheckingDuplicate(true);

    // 비동기 요청 지연 시뮬레이션
    await new Promise((resolve) => setTimeout(resolve, 1000));

    // 사업자 등록번호 중복 확인용 시뮬레이션
    const isDuplicate = regiNumber === '123-45-67890'; //실제 유효성 검사 로직으로 대체

    setIsDuplicateRegiNumber(isDuplicate);
    setIsCheckingDuplicate(false);

    if (isDuplicate) {
      alert('이미 등록된 사업자등록번호입니다.');
    }
  };

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
                placeholder="ex. 홍길동"
                type="text"
              />
            </div>
            <div className="flex space-x-4 justify-end py-2">
              <label>사업자등록번호</label>
              <div className="w-[78%] flex flex-row">
                <div className="pr-4 w-[100%]">
                  <input
                    className="border-[1px] border-[#9A9A9A] rounded-md w-[100%]  px-2 py-[2px]"
                    placeholder="ex. 123-45-67890"
                    type="text"
                    value={regiNumber}
                    onChange={handleRegiNumberChange}
                    maxLength={12}
                  />
                  {isRegiNumberIncomplete && (
                    <p className='pt-1 text-red-500'>사업자 등록번호는 10자리로 입력되어야 합니다.</p>
                  )}
                </div>
                <button
                  className={`rounded-md px-4 h-7 w-28 bg-[#4771B7] text-white ${
                    isRegiNumberValid ? 'cursor-pointer' : 'cursor-default'
                  }`}
                  type="button"
                  disabled={!isRegiNumberValid || isCheckingDuplicate}
                  onClick={handleDuplicateCheck}
                >
                  중복확인
                </button>
              </div>
            </div>
            <div className="flex space-x-5 justify-end py-2">
              <label className="">업체명</label>
              <input
                className="border-[1px] border-[#9A9A9A] rounded-md w-[78%]  px-2 py-[2px]"
                placeholder="ex. OO레저"
                type="text"
              />
            </div>
            <div className="flex space-x-5 justify-end py-2">
              <label className="">개업 일자</label>
              <input
                className="border-[1px] border-[#9A9A9A] rounded-md  w-[78%] px-2 py-[2px]"
                placeholder="2023-00-00"
                type="date"
              />
            </div>
            <div className="flex flex-row justify-end py-2">
              <div className="flex space-x-5 justify-end w-[55%]">
                <label className="w-[9%]">업태</label>
                <input
                  className="border-[1px] border-[#9A9A9A] rounded-md w-[60%]  px-2 py-[2px]"
                  type="text"
                  name="business"
                  value="스포츠 및 여가관련 서비스업"
                  readOnly
                  onClick={(e) => e.preventDefault()}
                  style={{ pointerEvents: 'none' }}
                />
              </div>
              <div className="flex space-x-5 justify-end w-[45%]">
                <label className="">업종</label>
                <div>
                  <SelectBox />
                </div>
              </div>
            </div>
            <div className="flex justify-center pt-4">
              <button
                className="font-semibold rounded-md text-xl px-14 py-3 bg-[#4771B7] text-white"
                type="submit"
              >
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
