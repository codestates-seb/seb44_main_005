import { atom } from 'recoil';

import { category } from '../dummy/category';

export const CategoryData = atom({
  key: 'CategoryData',
  default: category,
});
