import axios from "axios";
import api from "../../utils/axios";


export function CommuniyMy() {
  return api
    .get("community/mypage")
    .then((response) => {
      return Promise.resolve(response.data);
    })
    .catch((e) => {
      return Promise.reject(e);
    });
}
