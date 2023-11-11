import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useParams, useNavigate } from 'react-router-dom';
import CommentWrite from './CommentWrite';

const PostDetail = () => {
  const navigate = useNavigate();

  const { communityId, postId } = useParams();
  const [postDetail, setPostDetail] = useState(null);
  const [token, setToken] = useState('');

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

  return (
    <div className="post-detail">

      <h1>{postDetail.title}</h1>

      <div>
        <input
            type="text"
            value={token}
            onChange={(e) => setToken(e.target.value)}
            placeholder="토큰을 입력하세요"
        />
        <button onClick={deletePost}>삭제</button>
      </div>

      <Link
        to={`/softalk/community/${communityId}/post/${postId}/update-post`}
        state={{ postDetail: postDetail }}>
        수정
      </Link>

      <p>작성자: {postDetail.writerName}</p>
      <p>작성일: {postDetail.postCreatedAt}</p>
      <p>수정일: {postDetail.postUpdatedAt}</p>
      <p>내용: {postDetail.content}</p>

      <h3>이미지 목록</h3>
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

      <h3>댓글 목록</h3>
      <h2>댓글 작성</h2>
      <CommentWrite postId={postId}/>

      <ul>
        {postDetail.commentList && postDetail.commentList.map(comment => (
          <li key={comment.commentId}>
            <p>작성자: {comment.writerName}</p>
            <p>작성일: {comment.createdAt}</p>
            <p>수정일: {comment.updatedAt}</p>
            <p>내용: {comment.content}</p>
            <input
                type="text"
                value={token}
                onChange={(e) => setToken(e.target.value)}
                placeholder="토큰을 입력하세요"
            />

            <Link
                to={`/softalk/comment/${comment.commentId}`}
                state={{ token: token }}>
                    <button>삭제</button>
            </Link>

            {(!comment.children || comment.children.length === 0) && (
                <CommentWrite postId={postId} parentCommentId={comment.commentId} />
            )}

            <ul>
            {comment.children && comment.children.map(reply => (
                <li key={reply.commentId}>
                    <p>((대댓글))</p>
                    <p>작성자: {reply.writerName}</p>
                    <p>작성일: {reply.createdAt}</p>
                    <p>수정일: {reply.updatedAt}</p>
                    <p>내용: {reply.content}</p>
                    <input
                        type="text"
                        value={token}
                        onChange={(e) => setToken(e.target.value)}
                        placeholder="토큰을 입력하세요"
                    />
                    <button>삭제</button>
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
