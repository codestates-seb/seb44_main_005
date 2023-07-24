import emptyStar from '../../assets/emptyStar.svg';
import fillStar from '../../assets/fillStar.svg';

function ReviewCard({ review }) {
  const avg = String(review.rating * 10 * 2);
  const date = new Date(review.createdAt).toLocaleDateString();

  return (
    <section className="border-b-[1px] border-[#4771B7] my-5 p-3">
      <div className="relative w-[100px] top-1">
        <img className="absolute w-[100px] max-w-[100px]" src={emptyStar} alt="빈별" />
        <div className="absolute overflow-hidden w-[50%]" style={{ width: `${avg}%` }}>
          <img src={fillStar} className="card-star" alt="꽉찬별" />
        </div>
      </div>
      <div className="flex justify-between items-center mb-3">
        <div className="ml-28 font-medium">{review.nickname}</div>
        <div className="text-sm font-medium">{date}</div>
      </div>
      <div className="text-lg">{review.content}</div>
    </section>
  );
}

export default ReviewCard;