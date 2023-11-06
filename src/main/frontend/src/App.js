import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {Router, Routes, Route, Link} from "react-router-dom";

import PostList from './components/PostList';
import PostDetail from './components/PostDetail';

function App() {
    return (
        <div className="App">
            <Routes>
                <Route path="/softalk/community/:communityId/post/:postId" element={<PostDetail />} />
                <Route path="/softalk/community/:communityId" element={<PostList />} />
            </Routes>
        </div>
    );
}

export default App;
