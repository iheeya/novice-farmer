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
    // console.log(res.data);
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
      // console.log(res.data);
      return Promise.resolve(res.data);
    })
    .catch((err) => {
      // console.error(err);
      return Promise.reject(err);
    });
}

export function getInfoPlant():Promise<any>{
  return api
  .get("/info/plant")
  .then((res) => {
    // console.log(res.data);
    return Promise.resolve(res.data);
  })
  .catch((err) => {
    // console.error(err);
    return Promise.reject(err);
  });
}

export function getInfoPlantDetail(id: string): Promise<any> {
  return api
    .post("/info/plant",{"name":id})
    .then((res) => {
      // console.log(res.data);
      return Promise.resolve(res.data);
    })
    .catch((err) => {
      // console.error(err);
      return Promise.reject(err);
    });
}

export function getInfoFertilizer(): Promise<any> {
  return api
    .get("/info/fertilizer")
    .then((res) => {
      // console.log(res.data);
      return Promise.resolve(res.data);
    })
    .catch((err) => {
      // console.error(err);
      return Promise.reject(err);
    });
}

export function getInfoFertilizerDetail(id: string): Promise<any> {
  return api
    .post("/info/fertilizer",{"name":id})
    .then((res) => {
      // console.log(res.data);
      return Promise.resolve(res.data);
    })
    .catch((err) => {
      // console.error(err);
      return Promise.reject(err);
    });
}

export function getInfoPest(): Promise<any> {
  return api
    .get("/info/pest")
    .then((res) => {
      // console.log(res.data);
      return Promise.resolve(res.data);
    })
    .catch((err) => {
      // console.error(err);
      return Promise.reject(err);
    });
}

export function getInfoPestDetail(id: string): Promise<any> {
  return api
    .post("/info/pest",{"name":id})
    .then((res) => {
      // console.log(res.data);
      return Promise.resolve(res.data);
    })
    .catch((err) => {
      // console.error(err);
      return Promise.reject(err);
    });
}
