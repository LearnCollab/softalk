import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await axios.post('/auth/login', {
        email,
        password
      }, {
        // Axios 설정: 서버가 쿠키를 보내면 이를 브라우저에 저장하기 위함
        withCredentials: true
      });

      // JWT 토큰을 로컬 스토리지에 저장
      localStorage.setItem('jwt', response.data.accessToken);
      console.log(localStorage.getItem('jwt'));
      console.log(response.data);

      // 로그인 성공 시 메인 페이지로 리다이렉션
      //navigate('/');
      console.log("로그인 성공");
    } catch (error) {
      // 오류 처리
      if (axios.isAxiosError(error) && error.response) {
        setErrorMessage(error.response.data.message || '로그인 실패!');
      } else {
        setErrorMessage('로그인 요청 중 오류 발생!');
      }
      console.error('로그인 오류:', error);
    }
  };

  return (
    <div>
    <br/><br/><br/><br/>
      <h1>로그인</h1>
      <input
        type="text"
        placeholder="이메일"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="비밀번호"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleLogin}>로그인</button>
      {errorMessage && <div style={{color: 'red'}}>{errorMessage}</div>}
    </div>
  );
}

export default Login;
