import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const isLoginState = atom({
  key: 'isLoginState',
  default: false,
  effects_UNSTABLE: [persistAtom],
});
// 1. 로그인유효시간이 있는지 ? ->
// 2. 로드인 유효하지 않으 토큰으로 요청이 왔을때 어떤 에러를 보내주는가 ? 401
// 3. 401이 내려온 경우, app.ts를 감싸주는 에러바운더리 (약속된 에러) -> 로그인페이지로 보낸다.

export const isProfile = atom({
  key: 'isProfile',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

export const Role = atom({
  key: 'Role',
  default: '',
  effects_UNSTABLE: [persistAtom],
});
