import { useEffect, useState } from 'react';
import SelectBox from '../components/Partner/SelectBox';

import {
  RegiContainer,
  PartnerContainer,
  RegiTitle,
  FormContainer,
  RepreNameContainer,
  RegiNumberContainer,
  RegiNumberNoWrite,
  RegiNumberCorrect,
  RegiNumberWrong,
  CompanyName,
  CommonInput,
  RegiNumberInput,
  BusinessInput,
  BusinessContanier,
  SectorContainer,
  BusinessSectorContainer,
  FormRegiButton,
  FormRegiContainer,
  LabelStyle,
  OpeningContainer,
} from '../styles/Partner/Partner';

function Partner() {
  const [regiNumber, setRegiNumber] = useState('');
  const [repreName, setRepreName] = useState('');
  const [companyName, setCompanyName] = useState('');
  const [openingDate, setOpeningDate] = useState('');
  const [businessSector, setBusinessSector] = useState('');

  const [isInputTouched, setIsInputTouched] = useState(false);
  const [isCheckingDuplicate, setIsCheckingDuplicate] = useState(false);
  const [isDuplicateRegiNumber, setIsDuplicateRegiNumber] = useState(false);

  const handleRegiNumberChange = (e) => {
    const input = e.target.value.replace(/\D/g, '');
    const formattedInput = input.slice(0, 10);
    const formattedRegiNumber = formattedInput.replace(
      /(\d{3})(\d{2})(\d{5})/,
      '$1-$2-$3'
    );
    setRegiNumber(formattedRegiNumber);
  };

  const isRegiNumberValid = regiNumber.match(/^\d{3}-\d{2}-\d{5}$/);
  const isRegiNumberIncomplete = regiNumber.length > 0 && !isRegiNumberValid;

  useEffect(() => {
    setIsInputTouched(true);
  }, []);

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

  const isFormValid =
    regiNumber &&
    isRegiNumberValid &&
    repreName.length > 0 &&
    companyName.length > 0 &&
    openingDate.length > 0 &&
    businessSector.length > 0;

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = {
      owner: repreName,
      businessName: companyName,
      registrationNumber: regiNumber,
      businessCategory: businessSector,
    };

    try {
      const response = await fetch('/partners', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        // 성공적으로 등록된 경우 처리
        console.log('Status', response.status);
        if (response.status === 201) {
          console.log('201 Created');
        }
      } else {
        // 등록 실패한 경우 처리
        console.log('Status', response.status);
      }
    } catch (error) {
      // 예외 처리
      console.log('네트워크 오류: 파트너 등록에 실패하였습니다.');
    }
  };

  return (
    <PartnerContainer>
      <div className="p-10">
        <RegiContainer>
          <RegiTitle>파트너 등록하기</RegiTitle>
          <FormContainer>
            <RepreNameContainer>
              <label>대표자 명</label>
              <CommonInput
                placeholder="ex. 홍길동"
                type="text"
                value={repreName}
                onChange={(e) => setRepreName(e.target.value)}
              />
            </RepreNameContainer>
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
                    <RegiNumberWrong>
                      사업자 등록번호는 10자리로 입력되어야 합니다.
                    </RegiNumberWrong>
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
            <CompanyName>
              <label>업체명</label>
              <CommonInput
                placeholder="ex. OO레저"
                type="text"
                value={companyName}
                onChange={(e) => setCompanyName(e.target.value)}
              />
            </CompanyName>
            <OpeningContainer>
              <label>개업 일자</label>
              <CommonInput
                placeholder="2023-00-00"
                type="date"
                value={openingDate}
                onChange={(e) => setOpeningDate(e.target.value)}
              />
            </OpeningContainer>
            <BusinessSectorContainer>
              <BusinessContanier>
                <LabelStyle>업태</LabelStyle>
                <BusinessInput
                  type="text"
                  name="business"
                  value="스포츠 및 여가관련 서비스업"
                  readOnly
                  onClick={(e) => e.preventDefault()}
                  style={{ pointerEvents: 'none' }}
                />
              </BusinessContanier>
              <SectorContainer>
                <label>업종</label>
                <div>
                  <SelectBox
                    value={businessSector}
                    onChange={(e) => setBusinessSector(e.target.value)}
                  />
                </div>
              </SectorContainer>
            </BusinessSectorContainer>
            <FormRegiContainer>
              <FormRegiButton
                type="submit"
                disabled={!isFormValid}
                onClick={handleSubmit}
              >
                등록하기
              </FormRegiButton>
            </FormRegiContainer>
          </FormContainer>
        </RegiContainer>
      </div>
    </PartnerContainer>
  );
}

export default Partner;
