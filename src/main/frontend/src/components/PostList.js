import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Routes, Route, Link, useParams } from "react-router-dom";

import PostDetail from './PostDetail';

const PostList = () => {
  const [postList, setPostList] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(5); // 페이지당 아이템 수
  const {communityId} = useParams();
  const type = "all-posts"; // 전체 목록
  const sortBy = 0; // 최신순

  useEffect(() => {
    const url = `/softalk/community/${communityId}`;
    const params  = {
        type,
        sortBy,
        page: currentPage,
        size: pageSize
    };

    console.log(`*** Request URL: ${url}`, params);

    axios.get(url, { params })
    .then(response => {
      setPostList(response.data);
    })
    .catch(error => {
      console.error('커뮤니티 내 게시글 목록 조회 중 오류 발생: ', error);
    });
  }, [communityId, type, sortBy, currentPage]);

  if (!postList) {
    return <div>Loading...</div>;
  }

  const totalPages = Math.ceil(postList.totalCount / pageSize);

  const handlePageChange = (page) => {
    setCurrentPage(page);
  }

/*
  const handlePageSizeChange = (pageSize) => {
    setPageSize(pageSize);
    setCurrentPage(0); // 페이지 크기가 변경되면 첫 페이지로 이동
  }
*/

   const handleNextPage = () => {
     if (postList.hasNext) {
       setCurrentPage(currentPage + 1);
     }
   }

   const handlePreviousPage = () => {
     if (postList.hasPrevious) {
       setCurrentPage(currentPage - 1);
     }
   }

  return (
    <div className="post-list">
        <h1>게시글 목록</h1>

        <Link to={`/softalk/community/${communityId}/write-post`}>
            <button>게시글 작성하기</button>
        </Link>

        <ul>
              {postList.data.map(post => (
                <li key={post.postId}>
                  <Link to={`/softalk/community/${communityId}/post/${post.postId}`}>
                    <h2>{post.title}</h2>
                    <h3>postId: {post.postId}</h3>
                    <h3>communityId: {communityId}</h3>
                  </Link>

                  <p>내용: {post.content}</p>
                  <p>작성자: {post.writerName}</p>
                  <p>작성일: {post.postDate}</p>
                </li>
              ))}
         </ul>

      <div className="pagination">
              <button disabled={!postList.hasPrevious} onClick={handlePreviousPage}>
                이전
              </button>
              <button disabled={!postList.hasNext} onClick={handleNextPage}>
                다음
              </button>
      </div>

    </div>
  );
}

export default PostList;
