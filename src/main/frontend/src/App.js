import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import axios from 'axios';
import Login from './Auth/Login';
import Join from './Auth/Join';
import CreateCommunity from './Community/CreateCommunity';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/auth/login" element={<Login />} />
        <Route path="/auth/join" element={<Join />} />
        <Route path="/softalk/community" element={<CreateCommunity />} />
        <Route path="/" element={<Main />} />
      </Routes>
    </Router>
  );
}

function Main() {
  return (
    <div>
      <h1>Welcome to softalk!</h1>

      <div>
        <Link to="/auth/login">Login</Link>
      </div>

      <div>
        <Link to="/auth/join">Join</Link>
      </div>

      <div>
        <a href="/oauth2/authorization/kakao">Kakao Login (로컬에선 동작x)</a>
      </div>
    </div>
  );
}

export default App;