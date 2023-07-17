import { useState, useEffect } from "react";

import emptyStar from '../../assets/emptyStar.svg';
import fillStar from '../../assets/fillStar.svg';
import ReviewCard from "./ReviewCard";
import { useRecoilState } from "recoil";
import { ReviewsState } from "../../store/categoryDetailAtom";
import {
  ReviewBtn,
  ReviewLeftBox,
  ReviewRightBox,
  ReviewTextarea,
  Stars
} from "../../styles/CategoryDetail/Review";

function Review() {
  const API_URL = import.meta.env.VITE_APP_API_URL;
  const [stars, setStars] = useState([false, false, false, false, false]);
  const [form, setForm] = useState({});
  const [data, setData] = useRecoilState(ReviewsState);

  const starClickHandler = (idx) => {
    const result = [false, false, false, false, false].map((star, starIdx) => {
      if (starIdx <= idx) {
        return true;
      }
      return star;
    })
    setForm((prev) => ({...prev, rating: idx + 1}))
    setStars(result);
  };

  const contentChangeHandler = (e) => {
    setForm((prev) => ({...prev, content: e.target.value}));
  };

  const reviewPost = async () => {
    const storeId = location.pathname.substring(10);
    const accessToken = sessionStorage.getItem('Authorization');
    try {
      await fetch(`${API_URL}/reviews/${storeId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': accessToken
        },
        body: JSON.stringify(form)
      })
    }
    catch(error) {
      console.log(error);
    }
  }

  const reviewsFetch = async () => {
    const storeId = location.pathname.substring(10);
    try {
      const res = await fetch(`${API_URL}/reviews/${storeId}`);
      const json = await res.json();
      setData(json);
    }
    catch(error) {
      console.log(error);
    }
  };

  useEffect(() => {
    reviewsFetch();
  }, [])

  return (
    <section className="w-[600px] mb-10">
      <div className="text-2xl font-semibold mb-3">
        <span>리뷰</span>
        {data && <span className="text-[#4771B7] font-bold"> {data.reviewCount}</span>}
      </div>
      <div className="flex">
        <ReviewLeftBox>
          {data.ratingAvg && <div className="text-center text-3xl font-bold mb-2">{data.ratingAvg.toFixed(1)}</div>}
          <div className="relative">
            <img className="absolute max-w-[150px]" src={emptyStar} alt="빈별" />
              <div className="absolute overflow-hidden" style={{ width: `${data.ratingAvg * 10 * 2}%` }}>
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
            <ReviewBtn className="font-normal" type="button" onClick={reviewPost}>등록하기</ReviewBtn>
          </div>
          <ReviewTextarea onChange={contentChangeHandler} placeholder="액티온이 더 훈훈해지는 댓글 부탁드립니다." />
        </ReviewRightBox>
      </div>
      {data.reviews && data.reviews.map((review, idx) => <ReviewCard review={review} key={idx} />)}
    </section>
  );
}

export default Review;
