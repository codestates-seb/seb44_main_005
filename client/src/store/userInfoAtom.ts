import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

//로그인 여부 상태
export const userNameState = atom({
  key: 'userNameState',
  default: null,
  effects_UNSTABLE: [persistAtom],
});

export const isLoginState = atom({
  key: 'isLoginState',
  default: false,
  effects_UNSTABLE: [persistAtom],
});
