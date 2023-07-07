import React from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import tw from 'tailwind-styled-components';

import all from '../../assets/Category/all.svg';
import atv from '../../assets/Category/atv.svg';
import diving from '../../assets/Category/diving.svg';
import riding from '../../assets/Category/riding.svg';
import surf from '../../assets/Category/surf.svg';
import waterleisure from '../../assets/Category/waterleisure.svg';

function Categorybar() {
  const [searchParams] = useSearchParams();
  const test = searchParams.get('category_name');
  console.log(test);
  return (
    <div className="flex justify-center items-center my-7">
      <div className="flex w-[700px] justify-around text-sm grow-1">
        <Item>
          <Link to="/category?category_name=all">
            <img src={all} className="w-[25px] mb-2 ml-[2px]" />
            <span>전체</span>
          </Link>
        </Item>
        <Item>
          <Link to="/category?category_name=diving">
            <img src={diving} className="w-[25px] mb-2 ml-[30px]" />
            <span>스노쿨링/다이빙</span>
          </Link>
        </Item>
        <Item className="mr-[10px]">
          <Link to="/category?category_name=waterleisure">
            <img src={waterleisure} className="w-[25px] mb-2 ml-[12px]" />
            <span>수상레저</span>
          </Link>
        </Item>
        <Item className="mr-[10px]">
          <Link to="/category?category_name=surf">
            <img src={surf} className="w-[25px] mb-2" />
            <span>서핑</span>
          </Link>
        </Item>
        <Item className="mr-[10px]">
          <Link to="/category?category_name=riding">
            <img src={riding} className="w-[25px] h-[25px] mb-2" />
            <span>승마</span>
          </Link>
        </Item>
        <Item>
          <Link to="/category?category_name=atv">
            <img src={atv} className="w-[25px] h-[25px] mb-2 ml-[2px]" />
            <span className="mr-[10px]">ATV</span>
          </Link>
        </Item>
      </div>
    </div>
  );
}

const Item = tw.div`
  flex
  flex-col
  justify-center 
  items-center
`;

export default Categorybar;
