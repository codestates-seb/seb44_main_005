type ConfirmationModal = {
    message: string;
    onConfirm: () => void;
    onCancel: () => void;
}

function ConfirmationModal({ message, onConfirm, onCancel }) {
    return (
        <div>
            <p>{message}</p>
            <div className="space-x-2">
              <button className="bg-white border-[1px] border-[#4771B7] p-1 rounded-lg" onClick={onConfirm}>사진 삭제</button>
              <button className="bg-white border-[1px] border-[#4771B7] p-1 rounded-lg" onClick={onCancel}>삭제 취소</button>
            </div>
        </div>
    );
};

export default ConfirmationModal;