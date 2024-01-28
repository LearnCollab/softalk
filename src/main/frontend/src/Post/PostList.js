import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Routes, Route, Link, useParams, useNavigate } from "react-router-dom";
import { Button, Modal } from 'react-bootstrap';

import PostDetail from './PostDetail';
import 'bootstrap/dist/css/bootstrap.css';

const PostList = () => {
  const navigate = useNavigate();
  const [postList, setPostList] = useState({ data: [], hasNext: false, hasPrevious: false, totalCount: 0 });
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(8); // 페이지당 아이템 수
  const {communityId} = useParams();
  const type = "all-posts"; // 전체 목록
  const sortBy = 0; // 최신순
  const [showModal, setShowModal] = useState(false);


  useEffect(() => {
    window.scrollTo(0, 0);

    const url = `/softalk/community/${communityId}`;
    const params  = {
        type,
        sortBy,
        page: currentPage,
        size: pageSize
    };

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

   const handleWriteClick = () => {
       const token = localStorage.getItem('jwt');
       if (token == null || token == '') {
        setShowModal(true);
       } else {
        navigate(`/softalk/community/${communityId}/write-post`);
       }
   };

   function formatPostDate(postDateString) {

     const postDate = new Date(postDateString.replace(/(\d{2})\/(\d{2})\/(\d{2}) (\d{2}):(\d{2})/, '20$1-$2-$3T$4:$5:00'));
     const now = new Date();

     // 시간 차이를 밀리초 단위로 계산
     const diffMs = now - postDate;

     // 분, 시간, 일 단위로 차이 계산
     const diffMins = Math.floor(diffMs / 60000);
     const diffHours = Math.floor(diffMs / 3600000);
     const diffDays = Math.floor(diffMs / 86400000);

     if (diffMins === 0) {
        return "방금";
     } else if (diffMins < 60) {
        return diffMins + "분 전";
     } else if (diffHours < 24) {
        return diffHours + "시간 전";
     } else {
        const year = postDate.getFullYear();
        const month = postDate.getMonth() + 1;
        const day = postDate.getDate();

        if (year === now.getFullYear()) {
            return `${month}/${day}`;
        } else {
            return `${year % 100}/${month}/${day}`;
        }
     }
   }

  return (
    <main id="main">
      <div className="breadcrumbs">
        <div className="container">
          <div className="d-flex justify-content-between align-items-center">
            <h2>샘플 커뮤니티</h2>
            <ol>
              <li><Link to={`/`}>Home</Link></li>
              <li>샘플 커뮤니티</li>
            </ol>
          </div>
        </div>
      </div>

      <section id="posts-list" className="posts-list">
        <div className="container" data-aos="fade-up">
          <Button variant="info" size="sm" onClick={handleWriteClick} style={{ marginBottom: '10px'}}>
            <i className="bi bi-pencil-square"></i>&nbsp;글쓰기
          </Button>

          <Modal show={showModal} onHide={() => setShowModal(false)}>
            <Modal.Header closeButton>
              <Modal.Title>로그인 알림</Modal.Title>
            </Modal.Header>
            <Modal.Body>로그인 후 이용해 주세요</Modal.Body>
            <Modal.Footer>
              <Link to="/auth/login">
                <Button variant="primary">로그인 하러 가기</Button>
              </Link>
              <Button variant="secondary" onClick={() => setShowModal(false)}>
                닫기
              </Button>
            </Modal.Footer>
          </Modal>

          <div className="row g-5">
            <div className="col-lg-8">
              <div className="row gy-4 posts-list">
                {postList.data && postList.data.length > 0 && postList.data.map(post => (
                  <div className="col-lg-6" key={post.postId}>
                    <article className="d-flex flex-column">
                      <div className="post-img">
                        <img
                            src={post.thumbnailUrl ? post.thumbnailUrl : "/assets/img/blog/blog-1.jpg"}
                            className="img-fluid"
                            style={{ width: "270px", height: "270px" }}/>
                      </div>
                      <h2 className="title">
                        <Link to={`/softalk/community/${communityId}/post/${post.postId}`}>
                          {post.title}
                        </Link>
                      </h2>
                      <div className="meta-top">
                        <ul>
                          <li className="d-flex align-items-center"><i className="bi bi-person"></i>&nbsp;{post.writerName}</li>
                          <li className="d-flex align-items-center"><i className="bi bi-clock"></i>&nbsp;{formatPostDate(post.postDate)}</li>
                          <li className="d-flex align-items-center"><i className="bi bi-chat-dots"></i>&nbsp;{post.commentCount} Comments</li>
                        </ul>
                      </div>
                      <div className="read-more mt-auto align-self-end">
                        <Link to={`/softalk/community/${communityId}/post/${post.postId}`}>
                          자세히 보기
                        </Link>
                      </div>
                    </article>
                  </div>
                ))}
              </div>
              <div className="pagination">
                <button disabled={!postList.hasPrevious} onClick={handlePreviousPage}>
                  이전
                </button>
                <button disabled={!postList.hasNext} onClick={handleNextPage}>
                  다음
                </button>
              </div>
            </div>

            <div className="col-lg-4">
              <div className="sidebar">
                <div className="sidebar-item search-form">
                  <h3 className="sidebar-title">Search</h3>
                  <form action="" className="mt-3">
                    <input type="text"/>
                    <button type="submit"><i className="bi bi-search"></i></button>
                  </form>
                </div>
                <div className="sidebar-item categories">
                  <h3 className="sidebar-title">Categories</h3>
                  <ul className="mt-3">
                    <li><a href="#">General <span>(25)</span></a></li>
                    <li><a href="#">Lifestyle <span>(12)</span></a></li>
                    <li><a href="#">Travel <span>(5)</span></a></li>
                    <li><a href="#">Design <span>(22)</span></a></li>
                    <li><a href="#">Creative <span>(8)</span></a></li>
                    <li><a href="#">Education <span>(14)</span></a></li>
                  </ul>
                </div>
                <div className="sidebar-item recent-posts">
                  <h3 className="sidebar-title">Recent Posts</h3>
                  <div className="mt-3">
                    <div className="post-item mt-3">
                      <img src="assets/img/blog/blog-recent-1.jpg" alt="" className="flex-shrink-0"/>
                      <div>
                        <h4><a href="blog-post.html">Nihil blanditiis at in nihil autem</a></h4>
                        <time datetime="2020-01-01">Jan 1, 2020</time>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="sidebar-item tags">
                  <h3 className="sidebar-title">Tags</h3>
                  <ul className="mt-3">
                    <li><a href="#">App</a></li>
                    <li><a href="#">IT</a></li>
                    <li><a href="#">Business</a></li>
                    <li><a href="#">Mac</a></li>
                    <li><a href="#">Design</a></li>
                    <li><a href="#">Office</a></li>
                    <li><a href="#">Creative</a></li>
                    <li><a href="#">Studio</a></li>
                    <li><a href="#">Smart</a></li>
                    <li><a href="#">Tips</a></li>
                    <li><a href="#">Marketing</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
  );
}

export default PostList;
