import { useState } from "react";
import { useNavigate } from "react-router-dom";

function SignUp() {
  // 회원가입을 위한 상태 변수
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [nickName, setNickName] = useState("");
  const [age, setAge] = useState("");
  const [gender, setGender] = useState("");
  const [address, setAddress] = useState("");
  
  const navigate = useNavigate();

  // 회원가입 api 핸들러
  const handleSignUp = (event: { preventDefault: () => void; }) => {
    event.preventDefault();
    if (password !== passwordConfirm) {
      alert("Passwords do not match!");
      return;
    }

    // API 요청을 위한 데이터
    const signUpData = {
      email,
      password,
      nickName,
      age,
      gender,
      address
    };

    // 회원가입 API 호출 (여기서는 예시로 콘솔에 출력)
    console.log("회원가입 데이터:", signUpData);

    // 회원가입 후 페이지 이동
    navigate("/login");
  };

  return (
    <div style={{
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      borderRadius: '5%',
      padding: '5%',
      margin: '5%',
      width: '80vw',
      height: '50vh',
      border: '1px solid black'
    }}>
      <h1>Sign Up</h1>
      <form onSubmit={handleSignUp}>
        <input 
          type="email" 
          placeholder="email" 
          value={email} 
          onChange={(e) => setEmail(e.target.value)} 
        />
        <input 
          type="password" 
          placeholder="Password" 
          value={password} 
          onChange={(e) => setPassword(e.target.value)} 
        />
        <input 
          type="password" 
          placeholder="Password Confirm" 
          value={passwordConfirm} 
          onChange={(e) => setPasswordConfirm(e.target.value)} 
        />
        <input 
          type="text" 
          placeholder="Nick Name" 
          value={nickName} 
          onChange={(e) => setNickName(e.target.value)} 
        />
        <input 
          type="number" 
          placeholder="Age" 
          value={age} 
          onChange={(e) => setAge(e.target.value)} 
        />
        <input 
          type="text" 
          placeholder="Gender" 
          value={gender} 
          onChange={(e) => setGender(e.target.value)} 
        />
        <input 
          type="text" 
          placeholder="Address" 
          value={address} 
          onChange={(e) => setAddress(e.target.value)} 
        />
        <input type="submit" value="Sign Up" />
      </form>
    </div>
  );
}

export default SignUp;
