import SelectBox from './SelectBox';

import {
  BusinessContanier,
  SectorContainer,
  BusinessSectorContainer,
  LabelStyle,
  BusinessInput,
} from '../../styles/Partner/Partner';

function BusinessComponents({ businessSector, setBusinessSector }) {
  return (
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
  );
}

export default BusinessComponents;