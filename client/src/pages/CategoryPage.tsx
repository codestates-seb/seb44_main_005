import { useSearchParams, NavLink, useLocation } from 'react-router-dom';
import { useEffect } from 'react';
import { useRecoilState, useSetRecoilState } from 'recoil';
import 'aos/dist/aos.css';
import { ToastContainer } from 'react-toastify';

import CategoryCard from '../components/Categorybar/CategoryCard';
import {
  Style,
  CategoryContainer,
  Option,
  Category,
} from '../styles/Category/CategoryPage';
import { categoryData } from '../store/categoryAtom';
import { loading, search } from '../store/searchbarAtom';
import Loading from '../components/Loading/Loading';
import NoResult from '../components/NoResult/NoResult';
import top from '../assets/w_top.svg';
import { isLoginState } from '../store/userInfoAtom';
import { open } from '../store/dropdownAtom';

function CategoryPage() {
  const url = import.meta.env.VITE_APP_API_URL;
  const [searchParams] = useSearchParams();
  const location = useLocation();
  const categoryName = searchParams.get('category_name');
  const sort = searchParams.get('sort');
  const keywords = searchParams.get('keyword');

  // 전역 상태 변수
  const [isSearch, setIsSearch] = useRecoilState(search);
  const [isLoading, setIsLoading] = useRecoilState(loading);
  const [category, setCategory] = useRecoilState(categoryData);
  const setIsLoginState = useSetRecoilState(isLoginState);
  const setIsOpen = useSetRecoilState(open);

  useEffect(() => {
    const fetchData = async () => {
      let data;
      // 검색 조건이 있을 때
      if (keywords) {
        setIsSearch(true);
        setIsLoading(true);
        const res = await fetch(`${url}/search?keyword=${keywords}`, {
          headers: { Authorization: sessionStorage.getItem('Authorization') },
        });
        data = await res.json();
        if (res.status !== 200) throw res;
      } else {
        // 검색 조건이 없을 때
        const res = await fetch(
          `${url}/stores?category=${categoryName}&sort_field=${sort}`,
          {
            headers: { Authorization: sessionStorage.getItem('Authorization') },
          }
        );
        data = await res.json();
        setIsSearch(false);
        // 에러 처리
        if (res.status !== 200) throw res;
      }
      // 0.5초 동안 로딩 표시

      setTimeout(() => {
        setIsLoading(false);
      }, 500);
      setCategory(data);
    };

    fetchData();
  }, [categoryName, sort, keywords]);

  //토큰 만료시 로그아웃처리
  const handleToken = async () => {
    const accessToken = sessionStorage.getItem('Authorization');
    if (!accessToken) {
      await fetch(`/logout`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: sessionStorage.getItem('Authorization'),
          'Access-Control-Allow-Origin': '*',
        },
        credentials: 'include',
      });
      setIsOpen(false);
      setIsLoginState(false);
      localStorage.removeItem('recoil-persist');
      sessionStorage.removeItem('Authorization');
      sessionStorage.removeItem('memberId');
      sessionStorage.removeItem('access_token');
      // window.location.href = '/home';
    }
  };
  useEffect(() => {
    handleToken();
  });

  return (
    <Style>
      <ToastContainer
        toastClassName={
          'h-[20px] rounded-md text-sm font-medium bg-[#EDF1F8] text-[#4771B7] text-center shadow-sm'
        }
        position="top-center"
        limit={10}
        closeButton={false}
        autoClose={2000}
        hideProgressBar
      />
      <CategoryContainer style={{ display: isLoading ? 'none' : 'flex' }}>
        <span className="font-semibold text-2xl">
          전체상품 {category.pageInfo[0].storeCount}
        </span>
        <Option>
          {!isSearch && (
            <>
              <NavLink
                to={`/category?category_name=${categoryName}&sort=likeCount`}
                className={() => {
                  const url = new URLSearchParams(location.search);
                  const getTag = url.get('sort');

                  let className = 'mr-[25px]';
                  if (getTag === 'likeCount')
                    className += ' text-[#4771B7] font-medium';

                  return className;
                }}
              >
                • 관심순
              </NavLink>
              <NavLink
                to={`/category?category_name=${categoryName}&sort=rating`}
                className={() => {
                  const url = new URLSearchParams(location.search);
                  const getTag = url.get('sort');
                  let className = 'mr-[25px]';
                  if (getTag === 'rating')
                    className += ' text-[#4771B7] font-medium';
                  return className;
                }}
              >
                • 높은 평점순
              </NavLink>
              <NavLink
                to={`/category?category_name=${categoryName}&sort=lowPrice`}
                className={() => {
                  const url = new URLSearchParams(location.search);
                  const getTag = url.get('sort');
                  let className = 'mr-[25px]';
                  if (getTag === 'lowPrice')
                    className += ' text-[#4771B7] font-medium';
                  return className;
                }}
              >
                • 낮은 가격순
              </NavLink>
              <NavLink
                to={`/category?category_name=${categoryName}&sort=highPrice`}
                className={() => {
                  const url = new URLSearchParams(location.search);
                  const getTag = url.get('sort');
                  let className = 'mr-[25px]';
                  if (getTag === 'highPrice')
                    className += ' text-[#4771B7] font-medium';
                  return className;
                }}
              >
                • 높은 가격순
              </NavLink>
              <NavLink
                to={`/category?category_name=${categoryName}&sort=reviewCount`}
                className={() => {
                  const url = new URLSearchParams(location.search);
                  const getTag = url.get('sort');
                  let className = 'mr-[25px]';
                  if (getTag === 'reviewCount')
                    className += ' text-[#4771B7] font-medium';
                  return className;
                }}
              >
                • 리뷰 많은순
              </NavLink>
            </>
          )}
        </Option>
      </CategoryContainer>
      {isLoading ? (
        <Loading />
      ) : category.data.length > 0 ? (
        <Category>
          {category.data.map((el) => (
            <CategoryCard data={el} key={el.storeId} />
          ))}
        </Category>
      ) : isSearch ? (
        <NoResult />
      ) : null}
      <div className="flex flex-col items-center">
        <button
          className="fixed right-[30px] bottom-[40px] w-[50px] h-[50px] rounded-full text-white bg-[#4771B7] animate-topbounce"
          onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}
        >
          <img
            src={top}
            className="w-[50px] cursor-pointer duration-500 ease-in-out "
          />
        </button>
      </div>
    </Style>
  );
}

export default CategoryPage;
