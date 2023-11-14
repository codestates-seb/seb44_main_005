import { FormRegiContainer, FormRegiButton, DisableFormRegiButton } from '../../styles/Partner/Partner';

function FormRegistration ({ isFormValid, handleSubmit, businessSector, isDuplicateChecked }) {
  const isSelectedOptionSelected = businessSector === 'select';
  const formIsValid = isFormValid && !isSelectedOptionSelected && isDuplicateChecked;

  return (
    <FormRegiContainer>
      {!formIsValid ? (
        <DisableFormRegiButton
          type='button'
        >
          등록하기
        </DisableFormRegiButton>
      ) : (
        <FormRegiButton
          type='button'
          onClick={handleSubmit}
        >
          등록하기
        </FormRegiButton>
      )}
    </FormRegiContainer>
  );
}

export default FormRegistration;
