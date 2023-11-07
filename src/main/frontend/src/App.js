import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {Router, Routes, Route, Link} from "react-router-dom";

import PostList from './components/PostList';
import PostDetail from './components/PostDetail';
import PostWrite from './components/PostWrite';
import PostUpdate from './components/PostUpdate';

function App() {
    return (
        <div className="App">
            <Routes>
                <Route path="/softalk/community/:communityId/post/:postId" element={<PostDetail />} />
                <Route path="/softalk/community/:communityId" element={<PostList />} />
                <Route path="/softalk/community/:communityId/write-post" element={<PostWrite />} />
                <Route path="/softalk/community/:communityId/post/:postId/update-post" element={<PostUpdate />} />
            </Routes>
        </div>
    );
}

export default App;
