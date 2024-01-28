import React, { useState } from 'react';
import axios from 'axios';

function Join() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleJoin = async () => {
    try {
      await axios.post('/auth/join', {
        email,
        password,
        name
      });

      // 회원가입 성공 시 추가 액션 (예: 로그인 페이지로 리다이렉션)
      console.log('회원가입 성공!');
    } catch (error) {
      // 오류 처리
      if (axios.isAxiosError(error) && error.response) {
        setErrorMessage(error.response.data.message || '회원가입 실패!');
      } else {
        setErrorMessage('회원가입 요청 중 오류 발생!');
      }
      console.error('회원가입 오류:', error);
    }
  };

  return (
    <div>
    <br/><br/><br/><br/>
      <h1>회원가입</h1>
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
      <input
        type="text"
        placeholder="이름"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <button onClick={handleJoin}>회원가입</button>
      {errorMessage && <div style={{color: 'red'}}>{errorMessage}</div>}
    </div>
  );
}

export default Join;
