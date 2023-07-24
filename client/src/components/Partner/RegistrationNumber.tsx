import { useEffect, useState } from "react";
import {
  RegiNumberContainer,
  RegiNumberInput,
  RegiNumberNoWrite,
  RegiNumberCorrect,
  RegiNumberWrong,
  RegiNumberConfirm
} from "../../styles/Partner/Partner";

function RegistrationNumber({
  regiNumber,
  handleRegiNumberChange,
  isInputTouched,
  isRegiNumberIncomplete,
  isRegiNumberValid,
  setIsInputTouched,
  isDuplicateChecked,
  setIsDuplicateChecked
}) {
  const APIURL = import.meta.env.VITE_APP_API_URL;
  const [isDuplicate, setIsDuplicate] = useState(false);
  const [duplicateMessage, setDuplicateMessage] = useState('');

  const handleDuplicateCheck = async () => {
    try {
      const ACCESS_TOKEN = sessionStorage.getItem('Authorization')
      const res = await fetch(`${APIURL}/partners/verify?number=${regiNumber}`, {
        method: 'GET',
        headers: {
          'Authorization': ACCESS_TOKEN,
        },
      });
      if (res.ok) {
        if(res.status === 200) {
          setIsDuplicate(false);
          setDuplicateMessage('사용 가능한 사업자 등록번호입니다.');
          setIsDuplicateChecked(true);
          alert('사용 가능한 사업자등록번호입니다.');
        };
      } else {
        setIsDuplicate(true);
        setDuplicateMessage('중복된 사업자 등록번호입니다.')
        setIsDuplicateChecked(true);
        alert('중복된 사업자 등록번호입니다.');
      }
    } catch (error) {
      console.error('중복확인 중 에러가 발생했습니다.', error);
    }
  };

  useEffect(() => {
    setIsDuplicateChecked(false);
  }, [regiNumber]);

  return (
    <RegiNumberContainer>
      <label>사업자등록번호</label>
      <div className="w-[78%] flex flex-row">
        <div className="pr-4 w-[100%]">
          <RegiNumberInput
            placeholder="ex. 123-45-67890"
            type="text"
            value={regiNumber}
            onChange={handleRegiNumberChange}
            maxLength={12}
            onBlur={() => setIsInputTouched(true)}
          />
          {isInputTouched && !regiNumber && (
            <RegiNumberNoWrite>숫자만 입력해주세요.</RegiNumberNoWrite>
          )}
          {!isDuplicateChecked && isRegiNumberValid && (
            <RegiNumberConfirm>중복확인을 진행해주세요.</RegiNumberConfirm>
          )}
          {isRegiNumberValid && isDuplicateChecked && (
            <RegiNumberCorrect>{duplicateMessage}</RegiNumberCorrect>
          )}
          {isRegiNumberIncomplete && (
            <RegiNumberWrong>사업자 등록번호는 10자리로 입력되어야 합니다.</RegiNumberWrong>
          )}
          {isDuplicate && isDuplicateChecked && (
            <RegiNumberWrong>{duplicateMessage}</RegiNumberWrong>
          )}
          
        </div>
        <button
          className={`rounded-md px-4 h-7 w-28 bg-[#4771B7] text-white ${
            isRegiNumberValid ? 'cursor-pointer' : 'opacity-50 bg-[#4771B7] cursor-default'
          }`}
          type="button"
          disabled={!isRegiNumberValid}
          onClick={handleDuplicateCheck}
        >
          중복확인
        </button>
      </div>
    </RegiNumberContainer>
  );
}

export default RegistrationNumber;
