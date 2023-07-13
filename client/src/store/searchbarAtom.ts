import { RecoilState, atom } from 'recoil';

export const searchKeyword: RecoilState<string> = atom({
  key: 'keyword',
  default: '',
});
