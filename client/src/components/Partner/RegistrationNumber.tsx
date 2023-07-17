import { 
    RegiNumberContainer,
    RegiNumberInput,
    RegiNumberNoWrite,
    RegiNumberCorrect,
    RegiNumberWrong
 } from "../../styles/Partner/Partner";

function RegistrationNumber({ 
    regiNumber,
    handleRegiNumberChange,
    isInputTouched,
    isRegiNumberIncomplete,
    isRegiNumberValid,
    setIsInputTouched,
    handleDuplicateCheck,
    isCheckingDuplicate
 }) {
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
                  {isRegiNumberValid && (
                    <RegiNumberCorrect>올바른 입력입니다.</RegiNumberCorrect>
                  )}
                  {isRegiNumberIncomplete && (
                    <RegiNumberWrong>사업자 등록번호는 10자리로 입력되어야 합니다.</RegiNumberWrong>
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
            </RegiNumberContainer>
    );
};

export default RegistrationNumber;