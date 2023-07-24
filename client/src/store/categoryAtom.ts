import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

export interface CategoryData {
  pageInfo: { storeCount: number }[];
  data: any[];
}

const { persistAtom } = recoilPersist();

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
  effects_UNSTABLE: [persistAtom],
});
