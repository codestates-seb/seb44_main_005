type ConfirmationModal = {
    message: string;
    onConfirm: () => void;
    onCancel: () => void;
}

function ConfirmationModal({ message, onConfirm, onCancel }) {
    return (
        <div className="space-y-2">
            <p className="text-[16px]">{message}</p>
            <div className="space-x-3">
              <button className="bg-white border-[1px] border-[#4771B7] p-1 rounded-lg" onClick={onConfirm}>사진 삭제</button>
              <button className="bg-white border-[1px] border-[#4771B7] p-1 rounded-lg" onClick={onCancel}>삭제 취소</button>
            </div>
        </div>
    );
};

export default ConfirmationModal;