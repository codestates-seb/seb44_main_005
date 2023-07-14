import { FormRegiContainer, FormRegiButton } from '../../styles/Partner/Partner';

function FormRegistration ({ isFormValid, handleSubmit }) {
  return (
    <FormRegiContainer>
      <FormRegiButton
        type="submit"
        disabled={!isFormValid}
        onClick={handleSubmit}
      >
        등록하기
      </FormRegiButton>
    </FormRegiContainer>
  );
}

export default FormRegistration;
