import { 
    BioConfirmContainer,
    CheckMessage,
    CheckButtonContainer,
    ConfirmButton
} from "../../styles/MyPage/ConfirmationModal";

type ConfirmationModal = {
    message: string;
    onConfirm: () => void;
    onCancel: () => void;
}

function ConfirmationModal({ message, onConfirm, onCancel }) {
    return (
        <BioConfirmContainer>
            <CheckMessage>{message}</CheckMessage>
            <CheckButtonContainer>
              <ConfirmButton onClick={onConfirm}>사진 삭제</ConfirmButton>
              <ConfirmButton onClick={onCancel}>삭제 취소</ConfirmButton>
            </CheckButtonContainer>
        </BioConfirmContainer>
    );
};

export default ConfirmationModal;