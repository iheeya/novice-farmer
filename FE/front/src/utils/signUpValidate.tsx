import api from "./axios";
// 공용 함수
interface PasswordProps {
  password: string;
  passwordConf: string;
}

// 이메일 형식 검사
export function validateEmail(value: string): boolean {
  if (!value) return true;
  return /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(value);
}

// 자음, 모음 단독 불가
export function validateNickname(value: string | null): boolean {
  if (!value) return true;
  return /^(?!.*[ㄱ-ㅎㅏ-ㅣ])[A-Za-z0-9가-힣]{1,8}$/.test(value);
}

// 숫자, 영어, 최소 8글자
export function validatePassword(value: string): boolean {
  if (!value) return true;
  return /^(?=.*[0-9])(?=.*[a-z])(?=\S+$).{8,}$/.test(value);
}

// 비밀번호 일치 검사
export function passwordConfirm({ password, passwordConf }: PasswordProps) {
  return password === passwordConf;
}

