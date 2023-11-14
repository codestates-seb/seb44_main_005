import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const open = atom({
  key: 'open',
  default: false,
  effects_UNSTABLE: [persistAtom],
});
