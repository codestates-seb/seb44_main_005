import {
  BusinessCategory,
  BusinessSpace,
  BusinessCategoryTitle
} from '../../styles/MyPage/BusinessSpaceSection';

function BusinessSpaceSection({ partnerData, businessRegi }) {
  if(!partnerData) {
    return null;
  }

  return (
    <BusinessSpace>
      <BusinessCategory>
        <BusinessCategoryTitle>업태</BusinessCategoryTitle>
        {partnerData.stores?.map((_, index) => (
          <span key={index}>{businessRegi}</span>
        ))}
      </BusinessCategory>
      <BusinessCategory>
        <BusinessCategoryTitle>업종</BusinessCategoryTitle>
        {partnerData.stores?.map((store, index) => (
          <span key={index}>{store.category}</span>
        ))}
      </BusinessCategory>
      <BusinessCategory>
        <BusinessCategoryTitle>업체명</BusinessCategoryTitle>
        {partnerData.stores?.map((store, index) => (
          <span key={index}>{store.storeName}</span>
        ))}
      </BusinessCategory>
    </BusinessSpace>
  );
}

export default BusinessSpaceSection;
