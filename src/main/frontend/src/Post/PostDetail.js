import React, { useState, useEffect, useReducer } from 'react';
import axios from 'axios';
import { Link, useParams, useNavigate, useLocation } from 'react-router-dom';
import { Button, Modal } from 'react-bootstrap';
import CommentWrite from './CommentWrite';

const PostDetail = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { communityId, postId } = useParams();
  const [showModal, setShowModal] = useState(false);
  const [postDetail, setPostDetail] = useState(null);

  const token = localStorage.getItem('jwt');

  const [replyOpen, dispatch] = useReducer((state, action) => {
      return { ...state, [action.id]: action.value };
  }, {});

  useEffect(() => {

    const url = `/softalk/community/${communityId}/post/${postId}`;

    axios.get(url, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        setPostDetail(response.data);
    })
    .catch(error => {
        console.error('커뮤니티 내 게시글 상세 조회 중 오류 발생: ', error);
    });
  }, [communityId, postId, setPostDetail]);


  if (!postDetail) {
    return <div>Loading...</div>;
  }

  /* 게시글 삭제 */
//  const handleDelete = () => {
//    if (window.confirm('게시글을 정말 삭제하시겠습니까?')) {
//        deletePost();
//    }
//  };
  /*게시글 삭제*/
  const deletePost = async () => {
      const url = `/softalk/community/${communityId}/post/${postId}`;

      try {
        const response = await axios.delete(url, {
            headers: {
              Authorization: `Bearer ${token}`
            }
        });

        if (response.status >= 200 && response.status < 300) {
            alert('게시글 삭제 성공');
            navigate(`/softalk/community/${communityId}`);
        }
      } catch (error) {
        console.error('게시글 삭제 중 오류 발생: ', error);

        const statusCode = error.response.status;
        if(statusCode == 403){
            alert('게시글 삭제 중 문제가 발생했습니다: 게시글 삭제 권한 없음');
        }else if(statusCode == 500){
            alert('게시글 삭제 중 문제가 발생했습니다: 서버 내부 오류');
        }else{
            alert('게시글 삭제 중 문제가 발생했습니다: '+statusCode+' 오류');
        }
      }
  };

  /* 댓글 삭제 */
  const deleteComment = (commentId) => {
    const url = `/softalk/comment/${commentId}`;

    axios.delete(url, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })
          .then(response => {
            if(response.status == 200){
               alert('댓글 삭제 성공');
               window.location.reload();
            }
          })
          .catch(error => {
            if(error.response.status == 403){
                alert('댓글 삭제 불가: 댓글 삭제 권한 없음');
            }
            console.error('댓글 삭제 중 오류 발생: ', error);
          });
  };

  const goToUpdatePage = () => {
    navigate(`/softalk/community/${communityId}/post/${postId}/update-post`);
  };

  return (
    <main id="main">
        <div className="breadcrumbs">
              <div className="container">
                <div className="d-flex justify-content-between align-items-center">
                  <h2>샘플 커뮤니티</h2>
                  <ol>
                    <li><Link to={`/`}>Home</Link></li>
                    <li><Link to={`/softalk/community/${communityId}`}>샘플 커뮤니티</Link></li>
                    <li>샘플 커뮤니티 상세 페이지</li>
                  </ol>
                </div>
              </div>
        </div>

        <section id="blog" className="blog">

              <div className="container" data-aos="fade-up">
                {postDetail.writer && (
                  <div style={{ marginBottom: '10px' }}>
                    <Button variant="primary" onClick={goToUpdatePage}>수정</Button>
                    <Button variant="primary" onClick={() => setShowModal(true)} style={{ marginLeft: '7px' }}>삭제</Button>
                  </div>
                )}

                <Modal show={showModal} onHide={() => setShowModal(false)}>
                    <Modal.Header closeButton>
                        <Modal.Title>게시글 삭제 알림</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>게시글을 정말 삭제하시겠습니까?</Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={() => setShowModal(false)}>
                            아니요
                        </Button>
                        <Button variant="primary" onClick={deletePost}>
                            예
                        </Button>
                    </Modal.Footer>
                </Modal>

                <div className="row g-5">
                  <div className="col-lg-8">
                    <article className="blog-details">

                      <h2 className="title">{postDetail.title}</h2>

                      <div className="meta-top">
                        <ul>
                          <li className="d-flex align-items-center">
                            <i className="bi bi-person"></i>작성자: {postDetail.writerName}
                          </li>
                          <li className="d-flex align-items-center">
                            <i className="bi bi-clock"></i>작성일: {postDetail.postCreatedAt}
                          </li>
                          <li className="d-flex align-items-center">
                            <i className="bi bi-clock"></i>수정일: {postDetail.postUpdatedAt}
                          </li>
                        </ul>
                      </div>

                      <div className="content">
                        <p>
                          {postDetail.content}
                        </p>
                      </div>

                      <section id="portfolio" className="portfolio" data-aos="fade-up">
                        <div className="container-fluid" data-aos="fade-up" data-aos-delay="200">
                          <div className="portfolio-isotope" data-portfolio-filter="*" data-portfolio-layout="masonry" data-portfolio-sort="original-order">
                            <div className="row g-0 portfolio-container">
                              {postDetail.imageUrlList && postDetail.imageUrlList.length > 0 && (
                                postDetail.imageUrlList.map((imageUrl, index) => (
                                  <div className="col-xl-3 col-lg-4 col-md-6 portfolio-item" key={index}>
                                    <img src={imageUrl} className="img-fluid" alt={`Image ${index + 1}`} />
                                    <div className="portfolio-info">
                                      <a href={imageUrl} data-gallery="portfolio-gallery" className="glightbox preview-link">
                                        <i className="bi bi-zoom-in"></i>
                                      </a>
                                    </div>
                                  </div>
                                ))
                              )}
                            </div>
                          </div>
                        </div>
                      </section>

                      <div className="meta-bottom">
                        <i className="bi bi-folder"></i>
                        <ul className="cats">
                          <li><a href="#">Business</a></li>
                        </ul>

                        <i className="bi bi-tags"></i>
                        <ul className="tags">
                          <li><a href="#">Creative</a></li>
                          <li><a href="#">Tips</a></li>
                          <li><a href="#">Marketing</a></li>
                        </ul>
                      </div>
                    </article>

                    <br/>
                    <div className="comments">
                      <h4 className="comments-count">{postDetail.commentCount} Comments</h4>
                      <ul>
                        {postDetail.commentList && postDetail.commentList.map(comment => (
                          <li key={comment.commentId}>
                            <div id="comment-1" className="comment">
                              <div className="d-flex">
                                <div>
                                  <h5>
                                    {comment.writerName}
                                    {comment.writerName === postDetail.writerName && <strong> [작성자]</strong>}
                                  </h5>
                                  <div>작성일: {comment.createdAt}</div>
                                  <p>{comment.content}</p>
                                </div>
                              </div>
                            </div>

                            {comment.content !== "삭제된 댓글입니다." && (
                              <div>
                                <button onClick={() => deleteComment(comment.commentId)}>
                                  삭제
                                </button>

                                <button onClick={() => dispatch({id: comment.commentId, value: !replyOpen[comment.commentId]})}>
                                  대댓글
                                </button>

                                {replyOpen[comment.commentId] && <CommentWrite postId={postId} parentCommentId={comment.commentId} />}
                              </div>
                            )}

                            <ul>
                              {comment.children && comment.children.map(reply => (
                                <li key={reply.commentId}>
                                  <div className="comment comment-reply">
                                    <div className="d-flex">
                                      <div>
                                        <h5>
                                          {reply.writerName}
                                          {reply.writerName === postDetail.writerName && <strong> [작성자]</strong>}
                                        </h5>
                                        <div>작성일: {reply.createdAt}</div>
                                        <p>{reply.content}</p>
                                        <button onClick={() => deleteComment(reply.commentId)}>
                                          삭제
                                        </button>
                                      </div>
                                    </div>
                                  </div>
                                </li>
                              ))}
                            </ul>
                          </li>
                        ))}
                      </ul>

                      <CommentWrite postId={postId}/>

                    </div>
                  </div>
                </div>
              </div>
        </section>

    </main>

  );
}

export default PostDetail;
