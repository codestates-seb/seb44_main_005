import { atom } from 'recoil';

import {
  CategoryDetailType,
  ReviewsType,
  reserFormType,
} from '../intefaces/CategoryDetail';

export const CategoryDetailState = atom({
  key: 'CategoryDetailState',
  default: {
    storeName: '',
    category: '',
    body: '',
    latitude: 0,
    longitude: 0,
    kakao: '',
    contact: '',
    address: '',
    isLike: false,
    profileImg:'',
    createdAt: '',
    items: [],
    storeImages: []
  } as CategoryDetailType
})

export const ReserFormState = atom({
  key: 'ReserFormState',
  default: {
    reservationName: '',
    reservationPhone: '',
    reservationEmail: '',
    reservationDate: '',
    reservationItems: [],
    totalPrice: 0
  } as reserFormType,
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
  default: {} as ReviewsType
})

export const ReserDateState = atom({
  key: 'ReserDateState',
  default: new Date().toISOString().substring(0, 10)
})
