import { useState } from 'react';
import { SelectContainer } from '../../styles/Partner/SelectBox';

function SelectBox({ value, onChange }) {
  const [isSelectDisabled, setIsSelectDisabled] = useState(false);

  const handleSelectFocus = () => {
    setIsSelectDisabled(true);
  };

  return (
    <SelectContainer
      value={value}
      onChange={onChange}
      onFocus={handleSelectFocus}
    >
      <option disabled={isSelectDisabled} value="select">
        선택
      </option>
      <option key="sprtsService" value="스포츠 서비스업">
        스포츠 서비스업
      </option>
      <option key="groundSports" value="육상 오락관련 서비스업">
        육상 오락관련 서비스업
      </option>
      <option key="oceanSports" value="해상 오락관련 서비스업">
        해상 오락관련 서비스업
      </option>
    </SelectContainer>
  );
}

export default SelectBox;
