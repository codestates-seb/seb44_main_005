import { RecoilState, atom } from 'recoil';

export const searchKeyword: RecoilState<string> = atom({
  key: 'keyword',
  default: '',
});

export const search: RecoilState<boolean> = atom({
  key: 'search',
  default: false,
});

export const loading: RecoilState<boolean> = atom({
  key: 'loading',
  default: false,
});
