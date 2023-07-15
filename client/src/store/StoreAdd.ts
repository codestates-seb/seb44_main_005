import { atom } from "recoil"

export const StoreformState = atom({
  key: 'formState',
  default: {}
})

export const pageTitleState = atom({
  key: 'pageTitleState',
  default: '업체 등록하기'
})

export const FirstImgState = atom({
  key: 'FirstImgState',
  default: null as null | File
})

export const DetailImgsState = atom({
  key: 'DetailImgsState',
  default: []
})