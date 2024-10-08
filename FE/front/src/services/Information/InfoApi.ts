import api from "../../utils/axios";

export function getInfoPlace(): Promise<any> {
  return api
    .get("/info/place")
    .then((res) => {
      // console.log(res.data);
      return Promise.resolve(res.data);
    })
    .catch((err) => {
      // console.error(err);
      return Promise.reject(err);
    });
}

export function getInfoPlaceType():Promise<any>{
  return api
  .get("/info/place/type")
  .then((res) => {
    console.log(res.data);
    return Promise.resolve(res.data);
  })
  .catch((err) => {
    // console.error(err);
    return Promise.reject(err);
  });
}

export function getInfoPlaceDetail(id: string): Promise<any> {
  return api
    .post("/info/place/type",{"name":id})
    .then((res) => {
      console.log(res.data);
      return Promise.resolve(res.data);
    })
    .catch((err) => {
      // console.error(err);
      return Promise.reject(err);
    });
}
