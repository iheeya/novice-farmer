import axios from "axios";
import api from "../../utils/axios";

export function getTags() {
  return api
    .get("/community/tags/all")
    .then((response) => {
      return Promise.resolve(response.data);
    })
    .catch((e) => {
      return Promise.reject(e);
    });
}

interface SearchParams {
  page: number;
  size: number;
  filter: string;
  search: string; // search의 타입을 string으로 지정
}

export function getSearchResult({ page, size, filter, search }: SearchParams) {
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
