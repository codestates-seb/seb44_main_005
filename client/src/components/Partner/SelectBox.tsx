function SelectBox() {
    return (
        <select className='border-[1px] border-[#9A9A9A] rounded-md w-[100%] mr-[40px] px-2 py-[4px]'>
           <option key="select" value="select">선택</option>
           <option key="sprtsService" value="sportsService">스포츠 서비스업</option>
           <option key="groundSports" value="groundSports">육상 오락관련 서비스업</option>
           <option key="oceanSports" value="oceanSports">해상 오락관련 서비스업</option>
        </select>
    );
};

export default SelectBox;