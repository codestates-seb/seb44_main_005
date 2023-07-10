import React from 'react';
import dummyImg from '../dummy/mypagedummy.jpeg';
import dummyBio from '../dummy/mypage';
import SideBar from '../components/MyPage/SideBar';

function MyPage() {
  return (
    <div>
        <div className='flex flex-row justify-center items-start py-24 space-x-8'>
            <SideBar />
            <div className='border-[1px] border-[#4771B7] space-y-32 p-20'>
                <div className='space-y-7'>
                    <div className='grid justify-items-end'>
                      <button className='bg-[#4771B7] text-white px-4 py-1 rounded' type='button'>편집</button>
                    </div>
                    <div className='flex flex-col items-center space-y-2'>
                        <img className='w-[100px]' src={dummyImg} alt="dummy bio img" />
                        <div className='flex space-x-3'>
                            <button className='text-[12px] bg-[#EDF1F8] border-[1px] border-[#4771B7] rounded' type='button'>사진 변경</button>
                            <button className='text-[12px] bg-[#EDF1F8] border-[1px] border-[#4771B7] rounded' type='button'>사진 삭제</button>
                        </div>
                        <span className='font-medium text-xl'>Taewoo Kim</span>
                    </div>
                    <div className='space-y-7'>
                        <div className='space-x-10 text-lg'>
                            <span className='font-medium'>닉네임</span>
                            <span>Taewoo Kim</span>
                        </div>
                        <div className='space-x-10 text-lg'>
                            <span className='font-medium'>이메일</span>
                            <span>abc123@naver.com</span>
                        </div>
                        <div className='space-x-10 text-lg'>
                            <span className='font-medium'>연락처</span>
                            <span>010-1234-5678</span>
                        </div>
                    </div>
                </div>
                <div className='space-y-7'>
                    <div className='grid justify-items-end'>
                        <button className='bg-[#4771B7] text-white px-4 py-1 rounded' type='button'>등록한 업체보기</button>
                    </div>
                    <div className='flex flex-row space-x-10 justify-center'>
                        <div className='flex flex-col space-y-5 text-lg'>
                            <span className='font-semibold'>업태</span>
                            <span>스포츠 및 여가관련 서비스업</span>
                            <span>스포츠 및 여가관련 서비스업</span>
                            <span>스포츠 및 여가관련 서비스업</span>
                        </div>
                        <div className='flex flex-col space-y-5 text-lg'>
                            <span className='font-semibold'>업종</span>
                            <span>레저 좋아</span>
                            <span>레저 화이팅</span>
                            <span>태우네 레저</span>
                        </div>
                        <div className='flex flex-col space-y-5 text-lg'>
                            <span className='font-semibold'>업태</span>
                            <span>언더워터플레이 함덕점</span>
                            <span>언더워터플레이 애월점</span>
                            <span>언더워터플레이 김녕점</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
  );
}

export default MyPage;
