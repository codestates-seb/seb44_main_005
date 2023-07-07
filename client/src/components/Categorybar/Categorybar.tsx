import React from 'react';
import { Link } from 'react-router-dom';
import tw from 'tailwind-styled-components';

import all from '../../assets/Category/all.svg';
import atv from '../../assets/Category/atv.svg';
import diving from '../../assets/Category/diving.svg';
import riding from '../../assets/Category/riding.svg';
import surf from '../../assets/Category/surf.svg';
import waterleisure from '../../assets/Category/waterleisure.svg';

function Categorybar() {
  return (
    <div className="flex justify-center items-center mt-7">
      <div className="flex w-[700px] justify-around text-sm">
        <Item>
          <Link to="/a">
            <img src={all} className="w-[25px] mb-2" />
            <span>all</span>
          </Link>
        </Item>
        <Item>
          <Link to="/b">
            <img src={diving} className="w-[25px] mb-2 ml-[30px]" />
            <span>스노쿨링/다이빙</span>
          </Link>
        </Item>
        <Item>
          <Link to="/c">
            <img src={waterleisure} className="w-[25px] mb-2 ml-[14px]" />
            <span>수상레저</span>
          </Link>
        </Item>
        <Item>
          <Link to="/d">
            <img src={surf} className="w-[25px] mb-2" />
            <span>서핑</span>
          </Link>
        </Item>
        <Item>
          <Link to="/e">
            <img src={riding} className="w-[25px] mb-2" />
            <span>승마</span>
          </Link>
        </Item>
        <Item>
          <Link to="/e">
            <img src={atv} className="w-[25px] mb-2" />
            <span>ATV</span>
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
