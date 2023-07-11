import { useState } from 'react';
import { Link } from 'react-router-dom';

import {
  CardContainer,
  CardText,
  CardPrice,
  Text,
} from '../../styles/Categorybar/CategoryCard';
import { BsFillStarFill } from 'react-icons/bs';
import { PiHeartFill } from 'react-icons/pi';
import { PiHeart } from 'react-icons/pi';

type CProps = {
  data: {
    storeId: number;
    category: string;
    title: string;
    content: string;
    address: string;
    rating: number;
    reviewCount: string;
    price: number;
    isLike: boolean;
    img: string;
  };
};
function CategoryCard({ data }: CProps) {
  const { storeId, img, title, reviewCount, address, rating, price, isLike } =
    data;

  const [isHeart, setIsHeart] = useState(isLike);

  const onClickHeart = () => {
    setIsHeart(!isHeart);
  };
  return (
    <CardContainer>
      <img className="w-[250px] h-[198px] object-cover" src={img} />
      <CardText>
        <Link to={`/category/${storeId}`} className="font-semibold">
          {title}
        </Link>
        <Text>
          <span className="w-[20px] mr-[5px] mt-[2px]">
            <BsFillStarFill size="18" color="#4771B7" />
          </span>
          <span className="mr-[2px]">{rating}</span>
          <span className="mr-[12px]">({reviewCount})</span>
          <span>{address}</span>
        </Text>
        <CardPrice>
          <span>{price.toLocaleString('ko-KR')}원 ~</span>
          {isHeart ? (
            <PiHeartFill onClick={onClickHeart} size="24" color="#4771B7" />
          ) : (
            <PiHeart onClick={onClickHeart} size="24" color="#4771B7" />
          )}
        </CardPrice>
      </CardText>
    </CardContainer>
  );
}

export default CategoryCard;
