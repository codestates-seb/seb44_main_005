import { atom } from 'recoil';

export interface CategoryData {
  pageInfo: { storeCount: number }[];
  data: any[];
}
export const categoryData = atom<CategoryData>({
  key: 'categoryData',
  default: {
    pageInfo: [{ storeCount: 0 }],
    data: [],
  },

});
