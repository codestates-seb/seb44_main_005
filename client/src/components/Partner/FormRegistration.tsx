import { FormRegiContainer, FormRegiButton, DisableFormRegiButton } from '../../styles/Partner/Partner';

function FormRegistration ({ isFormValid, handleSubmit, businessSector, isDuplicateChecked }) {
  const isSelectedOptionSelected = businessSector === 'select';
  const formIsValid = isFormValid && !isSelectedOptionSelected && isDuplicateChecked;

  return (
    <FormRegiContainer>
      {!formIsValid ? (
        <DisableFormRegiButton
          type='submit'
        >
          등록하기
        </DisableFormRegiButton>
      ) : (
        <FormRegiButton
          type='submit'
          onClick={handleSubmit}
        >
          등록하기
        </FormRegiButton>
      )}
    </FormRegiContainer>
  );
}

export default FormRegistration;
