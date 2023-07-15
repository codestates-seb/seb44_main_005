import { atom } from "recoil"
import { StoreAddFormType } from "../intefaces/StoreAdd"

export const StoreformState = atom({
  key: 'formState',
  default: {
    storeName: '',
    body: '',
    address: '',
    kakao: '',
    contact: '',
    category: '',
    items: []
  } as StoreAddFormType
})

export const pageTitleState = atom({
  key: 'pageTitleState',
  default: '업체 등록하기'
})

export const ProductsState = atom({
  key: 'ProductsState',
  default: {
    itemName: '',
    price: 0,
    totalTicket: 0
  }
})

export const SendFirstImgState = atom({
  key: 'SendFirstImgState',
  default: null as null | File
})

export const SendDetailImgsState = atom({
  key: 'SendDetailImgsState',
  default: []
})

export const FirstImgState = atom({
  key: 'FirstImgState',
  default: null as null | string
})

export const DetailImgsState = atom({
  key: 'DetailImgsState',
  default: []
})