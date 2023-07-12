function StoreCheck() {
  return (
    <div className="border-[1px] border-[#4771B7] px-[50px] py-16 space-y-5">
      <span className="font-medium text-2xl pl-4">판매 서비스 관리</span>
      <div className="border-[1.5px] border-[#4771B7] w-[700px] h-[200px] rounded-lg p-5 flex flex-row justify-center items-center space-x-12">
        <div className="relative w-[146px] h-[127px]">
          <div className="absolute inset-0 w-[146px] h-[127px]">
            <img className="w-full h-full object-cover rounded-md" src="https://i.pinimg.com/564x/e9/73/64/e97364480b1511dbe7739a1d1b6f6762.jpg" alt="StoreCheck image" />
          </div>
        </div>
        <div className="flex flex-col w-[100%] space-y-20">
          <div className="pt-5">
            <span className="text-[20px] font-semibold">언더워터플레이 함덕점</span>
          </div>
          <div className="flex justify-end space-x-3 font-medium text-[15px]">
            <button className="bg-[#F3F5F7] p-2 rounded-lg" type="button">업체 수정</button>
            <button className="bg-[#F3F5F7] p-2 rounded-lg" type="button">업체 삭제</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default StoreCheck;
