import { atom } from 'recoil';

import {
  CategoryDetailType,
  ReviewsType,
  reserFormType,
} from '../intefaces/CategoryDetail';

export const CategoryDetailState = atom({
  key: 'CategoryDetailState',
  default: {} as CategoryDetailType,
});

export const ReserFormState = atom({
  key: 'ReservationFormState',
  default: {} as reserFormType,
});

export const itemsState = atom({
  key: 'itemsState',
  default: [],
});

export const totalPrice = atom({
  key: 'totalPrice',
  default: 0,
});

export const ReviewsState = atom({
  key: 'ReviewsState',
  default: {} as ReviewsType,
});
