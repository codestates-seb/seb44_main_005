import { atom } from 'recoil';

import { homeDataType } from '../intefaces/Home';

export const homeDataState = atom<homeDataType>({
  key: 'homeDataState',
  default: {} as homeDataType,
});
