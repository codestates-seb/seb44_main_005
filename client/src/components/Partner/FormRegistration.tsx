import { FormRegiContainer, FormRegiButton } from '../../styles/Partner/Partner';

function FormRegistration ({ isFormValid, handleSubmit, businessSector }) {
  const isSelectedOptionSelected = businessSector === 'select';
  const formIsValid = isFormValid && !isSelectedOptionSelected;

  return (
    <FormRegiContainer>
      <FormRegiButton
        type="submit"
        disabled={!formIsValid}
        onClick={handleSubmit}
      >
        등록하기
      </FormRegiButton>
    </FormRegiContainer>
  );
}

export default FormRegistration;
