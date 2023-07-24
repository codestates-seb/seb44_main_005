import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { isLoginState } from '../store/userInfoAtom';
import {
  RepresentativeName,
  RegistrationNumber,
  BusinessComponents,
  FormRegistration,
} from '../components/Partner';

import {
  RegiContainer,
  PartnerContainer,
  RegiTitle,
  FormContainer,
  CompanyName,
  CommonInput,
  OpeningContainer,
} from '../styles/Partner/Partner';

function Partner() {
  const APIURL = import.meta.env.VITE_APP_API_URL;
  const navigate = useNavigate();
  const [_, setIsLogin] = useRecoilState(isLoginState);
  const [regiNumber, setRegiNumber] = useState('');
  const [repreName, setRepreName] = useState('');
  const [companyName, setCompanyName] = useState('');
  const [openingDate, setOpeningDate] = useState('');
  const [selectedBusinessSector, setSelectedBusinessSector] =
    useState('select');
  const [isInputTouched, setIsInputTouched] = useState(false);
  const [isDuplicateChecked, setIsDuplicateChecked] = useState(false);

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
  const currentDate = new Date().toISOString().split('T')[0];

  useEffect(() => {
    setIsInputTouched(true);
  }, []);

  const isFormValid =
    regiNumber &&
    isRegiNumberValid &&
    repreName.length > 0 &&
    companyName.length > 0 &&
    openingDate.length > 0 &&
    selectedBusinessSector !== 'select';

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = {
      owner: repreName,
      businessName: companyName,
      registrationNumber: regiNumber,
      businessCategory: selectedBusinessSector,
    };

    try {
      const ACCESS_TOKEN = sessionStorage.getItem('Authorization');
      const response = await fetch(`${APIURL}/partners`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: ACCESS_TOKEN,
        },
        body: JSON.stringify(formData),
        credentials: 'include',
      });

      if (response.ok) {
        // 성공적으로 등록된 경우 처리
        if (response.status === 201) {
          alert('파트너등록이 완료되었습니다. 다시 로그인 해주세요.');
          setIsLogin(false);
          sessionStorage.removeItem('Authorization');
          navigate('/login');
        }
      } else {
        // 등록 실패한 경우 처리
        alert('파트너 등록에 실패했습니다.');
      }
    } catch (error) {
      // 예외 처리
      console.error('네트워크 오류: 파트너 등록에 실패하였습니다.', error);
    }
  };

  return (
    <PartnerContainer>
      <div className="p-10">
        <RegiContainer>
          <RegiTitle>파트너 등록하기</RegiTitle>
          <FormContainer>
            <RepresentativeName
              repreName={repreName}
              handleRepreNameChange={(e) => setRepreName(e.target.value)}
            />
            <RegistrationNumber
              regiNumber={regiNumber}
              handleRegiNumberChange={handleRegiNumberChange}
              isInputTouched={isInputTouched}
              isRegiNumberValid={isRegiNumberValid}
              isRegiNumberIncomplete={isRegiNumberIncomplete}
              setIsInputTouched={setIsInputTouched}
              isDuplicateChecked={isDuplicateChecked}
              setIsDuplicateChecked={setIsDuplicateChecked}
            />
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
                max={currentDate}
                onChange={(e) => setOpeningDate(e.target.value)}
              />
            </OpeningContainer>
            <BusinessComponents
              businessSector={selectedBusinessSector}
              setBusinessSector={setSelectedBusinessSector}
            />
            <FormRegistration
              isFormValid={isFormValid}
              handleSubmit={handleSubmit}
              businessSector={selectedBusinessSector}
              isDuplicateChecked={isDuplicateChecked}
            />
          </FormContainer>
        </RegiContainer>
      </div>
    </PartnerContainer>
  );
}

export default Partner;
