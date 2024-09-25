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

interface LoginResponse {
  firstLogin: boolean;
}

interface postSurveyInfoProps {
  plant: { id: number }[];
  place: { id: number }[];
}

// 로그인 함수
export function handleLogin({ email, password }: HandleLoginProps): Promise<LoginResponse> {
  return api
    .post("/user/login", {
      email,
      password,
    })
    .then((response) => {
      const { firstLogin, accessToken, refreshToken } = response.data;
      sessionStorage.setItem("accessToken", accessToken);
      sessionStorage.setItem("refreshToken", refreshToken);
      return Promise.resolve({ firstLogin });
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}

// 회원가입 함수
export function handleSignup({
  email,
  password,
  nickname,
  age,
  gender,
  address,
  pushAllow,
}: HandleSignupProps): Promise<void> {
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

// 설문조사 리스트 get 함수
export function getSurveyInfo(): Promise<any> {
  return api
    .get("/user/survey")
    .then((response) => {
      return Promise.resolve(response.data);
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}

// 설문조사 제출 함수
export function postSurveyInfo({ plant, place }: postSurveyInfoProps): Promise<any> {
  console.log({plant, place});
  return api
    .post("/user/survey", { plant, place })
    .then((response) => {
      return Promise.resolve();
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}

