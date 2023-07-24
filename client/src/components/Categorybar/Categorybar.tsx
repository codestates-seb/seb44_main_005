import { Link } from 'react-router-dom';

import {
  Item,
  CategoryBarContainer,
  CategoryBar,
} from '../../styles/Categorybar/Categorybar';
import all from '../../assets/Category/all.svg';
import atv from '../../assets/Category/atv.svg';
import diving from '../../assets/Category/diving.svg';
import riding from '../../assets/Category/riding.svg';
import surf from '../../assets/Category/surf.svg';
import waterleisure from '../../assets/Category/waterleisure.svg';

function Categorybar() {
  return (
    <CategoryBarContainer>
      <CategoryBar>
        <Item>
          <Link to="/category?category_name=all&sort=likeCount">
            <img src={all} className="w-[25px] mb-2 ml-[2px]" />
            <span>전체</span>
          </Link>
        </Item>
        <Item>
          <Link to="/category?category_name=스노클링/다이빙&sort=likeCount">
            <img src={diving} className="w-[25px] mb-2 ml-[30px]" />
            <span>스노클링/다이빙</span>
          </Link>
        </Item>
        <Item className="mr-[10px]">
          <Link to="/category?category_name=수상레저&sort=likeCount">
            <img src={waterleisure} className="w-[25px] mb-2 ml-[12px]" />
            <span>수상레저</span>
          </Link>
        </Item>
        <Item className="mr-[10px]">
          <Link to="/category?category_name=서핑&sort=likeCount">
            <img src={surf} className="w-[25px] mb-2" />
            <span>서핑</span>
          </Link>
        </Item>
        <Item className="mr-[10px]">
          <Link to="/category?category_name=승마&sort=likeCount">
            <img src={riding} className="w-[25px] h-[25px] mb-2" />
            <span>승마</span>
          </Link>
        </Item>
        <Item>
          <Link to="/category?category_name=ATV&sort=likeCount">
            <img src={atv} className="w-[25px] h-[25px] mb-2 ml-[2px]" />
            <span className="mr-[10px]">ATV</span>
          </Link>
        </Item>
      </CategoryBar>
    </CategoryBarContainer>
  );
}

export default Categorybar;
