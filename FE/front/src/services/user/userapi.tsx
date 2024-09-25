import api from "../../utils/axios";

interface HandleLoginProps {
  email: string;
  password: string;
}

interface HandleSignupProps {
  email: string;
  password: string;
  nickname: string;
  age: number;
  gender: string;
  address: string;
  pushAllow: boolean;
}

interface LoginResponse{
  firstLogin: boolean;
}

export function handleLogin({ email, password }: HandleLoginProps): Promise<LoginResponse> {
  return api
    .post("/user/login", {
      email,
      password,
    })
    .then((response) => {
      const { firstLogin } = response.data;
      return Promise.resolve({firstLogin});
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}

export function handleSignup({email,password,nickname,age,gender,address,pushAllow}:HandleSignupProps):Promise<void>{
  return api
  .post("/user", {
    email,
    password,
    nickname,
    age,
    gender,
    address,
    pushAllow,
  })
  .then((response) => {
    return Promise.resolve();
  })
  .catch((error) => {    
    return Promise.reject(error);
  });
}

export function getSurveyInfo():Promise<any>{
  return api
  .get("/user/survey")
  .then((response) => {
    return Promise.resolve(response.data);
  })
  .catch((error) => {
    return Promise.reject(error);
  });
}

export function postSurveyInfo():Promise<any>{
  return api
  .post("/user/survey")
  .then((response) => {
    return Promise.resolve();
  })
  .catch((error) => {
    return Promise.reject(error);
  });
}