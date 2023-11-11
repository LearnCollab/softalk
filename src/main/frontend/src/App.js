import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import axios from 'axios';
import Login from './Auth/Login';
import Join from './Auth/Join';
import CreateCommunity from './Community/CreateCommunity';

import PostList from './Post/PostList';
import PostDetail from './Post/PostDetail';
import PostWrite from './Post/PostWrite';
import PostUpdate from './Post/PostUpdate';
import CommentEdit from './Post/CommentEdit';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/auth/login" element={<Login />} />
        <Route path="/auth/join" element={<Join />} />
        <Route path="/softalk/community" element={<CreateCommunity />} />
        <Route path="/" element={<Main />} />

        <Route path="/softalk/community/:communityId/post/:postId" element={<PostDetail />} />
        <Route path="/softalk/community/:communityId" element={<PostList />} />
        <Route path="/softalk/community/:communityId/write-post" element={<PostWrite />} />
        <Route path="/softalk/community/:communityId/post/:postId/update-post" element={<PostUpdate />} />
        <Route path="/softalk/comment/:commentId" element={<CommentEdit />} />
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

      <div>
        <a href="/softalk/community/1">샘플 커뮤니티 바로가기</a>
      </div>
    </div>
  );
}

export default App;
