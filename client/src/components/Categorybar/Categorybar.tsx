import React from 'react';
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
          <Link to="/category?category_name=all&sort=recommend">
            <img src={all} className="w-[25px] mb-2 ml-[2px]" />
            <span>전체</span>
          </Link>
        </Item>
        <Item>
          <Link to="/category?category_name=diving&sort=recommend">
            <img src={diving} className="w-[25px] mb-2 ml-[30px]" />
            <span>스노쿨링/다이빙</span>
          </Link>
        </Item>
        <Item className="mr-[10px]">
          <Link to="/category?category_name=waterleisure&sort=recommend">
            <img src={waterleisure} className="w-[25px] mb-2 ml-[12px]" />
            <span>수상레저</span>
          </Link>
        </Item>
        <Item className="mr-[10px]">
          <Link to="/category?category_name=surf&sort=recommend">
            <img src={surf} className="w-[25px] mb-2" />
            <span>서핑</span>
          </Link>
        </Item>
        <Item className="mr-[10px]">
          <Link to="/category?category_name=riding&sort=recommend">
            <img src={riding} className="w-[25px] h-[25px] mb-2" />
            <span>승마</span>
          </Link>
        </Item>
        <Item>
          <Link to="/category?category_name=atv&sort=recommend">
            <img src={atv} className="w-[25px] h-[25px] mb-2 ml-[2px]" />
            <span className="mr-[10px]">ATV</span>
          </Link>
        </Item>
      </CategoryBar>
    </CategoryBarContainer>
  );
}

export default Categorybar;
