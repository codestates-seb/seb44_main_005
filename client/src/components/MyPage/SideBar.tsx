function SideBar() {
    return (
        <div className='border-[1px] border-[#4771B7] h-[100%] px-10 py-16 space-y-20'>
                <div className='flex flex-col space-y-8'>
                    <span className='text-xl font-semibold'>마이페이지</span>
                    <div className='text-lg flex flex-col space-y-8 pl-5'>
                        <a>내 정보 관리</a>
                        <a>위시리스트</a>
                        <a>예약 내역 조회</a>
                    </div>
                </div>
                <div className='flex flex-col space-y-8'>
                    <span className='text-xl font-semibold'>파트너쉽</span>
                    <div className='text-lg pl-5'>
                        <a>판매 서비스 관리</a>
                    </div>
                </div>
            </div>
    );
};

export default SideBar;