import React from 'react';
import tw from 'tailwind-styled-components';
import { AiOutlineStar } from 'react-icons/ai';
import { recommend } from '../../dummy/main';
type CProps = {
  data: {
    store_id: number;
    store_name: string;
    body: string;
    img: string;
  };
};
function CategoryCard({ data }: CProps) {
  const { img, store_name } = data;
  return (
    <CardContainer href="/[category]/:id">
      <img className="w-[250px] h-[198px]" src={img} />
      <CardText>
        <span className="font-semibold">{store_name}</span>
        <Text>
          <span className="w-[20px] mr-[5px]">
            <AiOutlineStar size="24" color="#4771B7" />
          </span>
          <span>4.5</span>
          <span className="mr-[15px]">(12)</span>
          <span>제주특별자치도 제주시 조천읍 조함해안로 321-21</span>
        </Text>
      </CardText>
      <CardPrice></CardPrice>
    </CardContainer>
  );
}

const CardContainer = tw.a`
  flex
  w-[800px]
  h-[200px]
  border
  my-6
  border-[#4771B7]
`;
const CardText = tw.div`
  flex
  flex-col
  items-start
  p-5
`;
const CardPrice = tw.div``;
const Text = tw.div`
  flex  
  mt-[5px]
`;

export default CategoryCard;
