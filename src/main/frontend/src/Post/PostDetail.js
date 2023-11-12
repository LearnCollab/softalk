import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useParams, useNavigate, useLocation } from 'react-router-dom';
import CommentWrite from './CommentWrite';

const PostDetail = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const { communityId, postId } = useParams();
  const [postDetail, setPostDetail] = useState(null);

  const token = localStorage.getItem('jwt');

  useEffect(() => {

    const url = `/softalk/community/${communityId}/post/${postId}`;

    axios.get(url)
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
  const deletePost = () => {
      const url = `/softalk/community/${communityId}/post/${postId}`;

      axios.delete(url, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      })
      .then(response => {
        if(response.status == 200){
           alert('게시글 삭제 성공');
           navigate(`/softalk/community/${communityId}`);
        }
      })
      .catch(error => {
        if(error.response.status == 403){
            alert('게시글 삭제 불가: 게시글 삭제 권한 없음');
        }
        console.error('게시글 삭제 중 오류 발생: ', error);
      });
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

  return (
    <div className="post-detail">

      <h1>{postDetail.title}</h1>

      <Link
        to={`/softalk/community/${communityId}/post/${postId}/update-post`}
        state={{ postDetail: postDetail }}>
        <button>수정</button>
      </Link>
      <button onClick={deletePost}>삭제</button>

      <p>작성자: {postDetail.writerName}</p>
      <p>작성일: {postDetail.postCreatedAt}</p>
      <p>수정일: {postDetail.postUpdatedAt}</p>
      <p>내용: {postDetail.content}</p>

      <h3>※ 이미지 목록 ※</h3>
      <ul>
        {postDetail.imageUrlList && postDetail.imageUrlList.map((imageUrl, index) => (
          <li key={index}>
            <img
                src={imageUrl}
                alt={`Image ${index + 1}`}
                style={{ maxWidth: '200px', maxHeight: '200px' }}
            />
          </li>
        ))}
      </ul>

      <h3>※ 댓글 목록 ※</h3>
      <div><b>댓글 작성</b></div>
      <CommentWrite postId={postId}/>

      <ul>
        {postDetail.commentList && postDetail.commentList.map(comment => (
          <li key={comment.commentId}>
            <p>작성자: {comment.writerName}</p>
            <p>작성일: {comment.createdAt}</p>
            <p>수정일: {comment.updatedAt}</p>
            <p>내용: {comment.content}</p>

            {comment.content !== "삭제된 댓글입니다." && (
            <div>
                <button onClick={() => deleteComment(comment.commentId)}>
                    삭제
                </button>
                <CommentWrite postId={postId} parentCommentId={comment.commentId} />
            </div>
            )}

            <ul>
            {comment.children && comment.children.map(reply => (
                <li key={reply.commentId}>
                    <p>((대댓글))</p>
                    <p>작성자: {reply.writerName}</p>
                    <p>작성일: {reply.createdAt}</p>
                    <p>수정일: {reply.updatedAt}</p>
                    <p>내용: {reply.content}</p>
                    <button onClick={() => deleteComment(reply.commentId)}>
                        삭제
                    </button>
                </li>
            ))}
            </ul>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default PostDetail;
