import { atom } from 'recoil';

export interface CategoryData {
  pageInfo: { storeCount: number }[];
  data: [];
}
export const categoryData = atom<CategoryData>({
  key: 'categoryData',
  default: {
    pageInfo: [{ storeCount: 0 }],
    data: [],
  },
});

export const Heart = atom<boolean>({
  key: 'Heart',
  default: false,
});
