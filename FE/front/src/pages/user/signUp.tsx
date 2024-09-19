import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "../../styles/user/signUp.module.css";

function SignUp() {
  // 회원가입을 위한 상태 변수
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [nickName, setNickName] = useState("");
  const [age, setAge] = useState("");
  const [gender, setGender] = useState("");
  const [address, setAddress] = useState("");
  const [pushNotification, setPushNotification] = useState(false); // 푸시 알림 동의 여부

  const navigate = useNavigate();

  // 회원가입 API 핸들러
  const handleSignUp = (event: { preventDefault: () => void }) => {
    event.preventDefault();

    // 비밀번호와 비밀번호 확인이 다를 경우 알림
    if (password !== passwordConfirm) {
      alert("비밀번호가 일치하지 않습니다");
      return;
    }

    // API 요청을 위한 데이터
    const signUpData = {
      email,
      password,
      nickName,
      age,
      gender,
      address,
      pushNotification,
    };

    // 회원가입 API 호출 (여기서는 예시로 콘솔에 출력)
    console.log("회원가입 데이터:", signUpData);

    // 회원가입 후 로그인 페이지로 이동 (실제 API 요청 후에 추가해야 함)
    // navigate("/login");
  };

  return (
    <div className={styles.container}>
      <img src="../../assets/logo.png" alt="새싹 농부" className={styles.logo} />
      <h2 className={styles.title}>새싹 농부</h2>

      {/* onSubmit에서 handleSignUp 함수 실행 */}
      <form className={styles.form} onSubmit={handleSignUp}>
        <div className={styles.duplicateCheck}>
          <input
            type="email"
            placeholder="Email"
            className={styles.input}
            value={email}
            onChange={(e) => setEmail(e.target.value)} // 입력 값이 상태와 동기화
            required
          />
          <button type="button" className={styles.duplicateButton} onClick={handleSignUp}>
            중복확인
          </button>
        </div>

        <input
          type="password"
          placeholder="Password"
          className={styles.input}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Report Password"
          className={styles.input}
          value={passwordConfirm}
          onChange={(e) => setPasswordConfirm(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Nick Name"
          className={styles.input}
          value={nickName}
          onChange={(e) => setNickName(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Age"
          className={styles.input}
          value={age}
          onChange={(e) => setAge(e.target.value)}
          required
        />
        <select className={styles.select} value={gender} onChange={(e) => setGender(e.target.value)} required>
          <option value="">성별</option>
          <option value="MALE">남자</option>
          <option value="FEMALE">여자</option>
        </select>
        <input
          type="text"
          placeholder="Address"
          className={styles.input}
          value={address}
          onChange={(e) => setAddress(e.target.value)}
          required
        />

        {/* 푸시 알림 동의 체크박스 */}
        <div className={styles.checkboxContainer}>
          <input
            type="checkbox"
            className={styles.checkbox}
            checked={pushNotification}
            onChange={(e) => setPushNotification(e.target.checked)}
          />
          <label>푸시 알림 동의</label>
        </div>

        <button type="submit" className={styles.submitButton}>
          회원가입 하기
        </button>
      </form>
    </div>
  );
}

export default SignUp;
