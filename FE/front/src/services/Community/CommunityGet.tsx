import api from "../../utils/axios";

interface SearchParams {
    page: number;
    size: number;
    filter: string;
    search: string; // search의 타입을 string으로 지정
  }
  
  export function getArticle({ page, size, filter, search }: SearchParams) {
    return api
      .get("community", {
        params: {
          page,
          size,
          filter,
          search,
        },
      })
      .then((response) => {
        return Promise.resolve(response.data);
      })
      .catch((e) => {
        return Promise.reject(e);
      });
  }