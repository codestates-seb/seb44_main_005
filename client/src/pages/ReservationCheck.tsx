function ReservationCheck() {
  return (
    <div className="border-[1px] border-[#4771B7] p-5 space-y-5">
      <p className="font-normal text-2xl p-4">예약 내역 조회</p>
      <div className="border-[1.5px] border-[#4771B7] w-[800px] h-[200px] rounded-lg p-5 flex flex-row justify-center items-center space-x-4">
        <div className="w-[146px] h-[127px] overflow-hidden relative rounded-md">
          <img className="absolute w-[100%]" src="https://i.pinimg.com/564x/50/26/9b/50269b9f5568fe624274a54e141c0bdb.jpg" alt="reservation image" />
        </div>
        <div className="flex flex-col w-[100%] space-y-2">
          <div className="flex justify-end w-[100%]">
            <div className="font-medium text-white text-[15px] bg-[#4771B7] w-[77px] h-[27px] flex justify-center items-center">
              <span>예약완료</span>
            </div>
          </div>
          <div className="space-y-2">
            <div>
              <span className="text-[16px]">2023년 7월 12일</span>
            </div>
            <div className="space-x-3">
              <span className="text-[20px] font-semibold">언더워터플레이 함덕점</span>
              <span className="text-[15px] text-[#868686] font-medium">총 4개 상품 결제</span>
            </div>
            <div>
              <span className="text-[15px] font-semibold">결제금액: 20,000원</span>
            </div>
          </div>
          <div className="flex justify-end space-x-3 font-medium text-[15px]">
            <button className="bg-[#F3F5F7] p-2 rounded-lg" type="button">예약 수정</button>
            <button className="bg-[#F3F5F7] p-2 rounded-lg" type="button">예약 삭제</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReservationCheck;
