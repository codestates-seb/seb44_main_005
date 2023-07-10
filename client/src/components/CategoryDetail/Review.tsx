import { useState } from "react";
import tw from "tailwind-styled-components";

import emptyStar from '../../assets/emptyStar.svg';
import fillStar from '../../assets/fillStar.svg';
import ReviewCard from "./ReviewCard";

function Review({ data }) {
  const [stars, setStars] = useState([false, false, false, false, false])
  const starClickHandler = (idx) => {
    const result = [false, false, false, false, false].map((star, starIdx) => {
      if (starIdx <= idx) {
        return true;
      }
      return star;
    })
    setStars(result);
  }
  const avg = String(data.ratingAvg * 10 * 2);

  return (
    <section className="w-[600px] mb-10">
      <div className="text-2xl font-semibold mb-3">
        <span>리뷰</span>
        <span className="text-[#4771B7] font-bold"> {data.reviewCount}</span>
      </div>
      <div className="flex">
        <ReviewLeftBox>
          <div className="text-center text-3xl font-bold mb-2">{data.ratingAvg.toFixed(1)}</div>
          <div className="relative">
            <img className="absolute max-w-[150px]" src={emptyStar} alt="빈별" />
            <div className="absolute overflow-hidden" style={{ width: `${avg}%` }}>
              <img src={fillStar} className="star" alt="꽉찬별" />
            </div>
          </div>
        </ReviewLeftBox>
        <ReviewRightBox>
          <div className="flex justify-between text-sm font-semibold">
            <div className="pt-1">레저는 만족 하셨나요?</div>
            <div className="mr-12">
              {stars.map((star, idx) => {
                return star ? <Stars onClick={() => starClickHandler(idx)} key={idx}>★</Stars> :
                <Stars onClick={() => starClickHandler(idx)} key={idx}>☆</Stars>
              })}
            </div>
            <ReviewBtn className="font-normal" type="button">등록하기</ReviewBtn>
          </div>
          <ReviewTextarea placeholder="액티온이 더 훈훈해지는 댓글 부탁드립니다." />
        </ReviewRightBox>
      </div>
      {data.reviews.map((review, idx) => <ReviewCard review={review} key={idx} />)}
    </section>
  );
}

export default Review;

const ReviewLeftBox = tw.div`
  w-[190px] h-[150px]
  bg-[#EDF1F8]
  mr-[10px] px-5 py-10
  rounded-[5px]
`
;
const ReviewRightBox = tw.div`
  w-[400px] h-[150px]
  bg-[#EDF1F8]
  rounded-[5px]
  p-5
`;

const ReviewBtn = tw.button`
  bg-[#4771B7]
  px-3 py-1
  text-white
  rounded-[5px]
`;

const ReviewTextarea = tw.textarea`
  mt-3 p-2
  w-full h-[70px]
  border-[1px] border-[#4771B7]
  rounded-[5px]
  text-sm
  font-medium
`;

const Stars = tw.span`
  text-[#4471B7] text-lg
  cursor-pointer
`;