import { CommonInput, RepreNameContainer } from "../../styles/Partner/Partner";

function RepresentativeName({ repreName, handleRepreNameChange }) {
    return (
        <RepreNameContainer>
            <label>대표자 명</label>
            <CommonInput
              placeholder="ex. 홍길동"
              type="text"
              value={repreName}
              onChange={handleRepreNameChange}
            />
        </RepreNameContainer>
    );
};

export default RepresentativeName;