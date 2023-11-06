import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';

const PostDetail = () => {
  const { communityId, postId } = useParams();
  const [postDetail, setPostDetail] = useState(null);

  useEffect(() => {

    const url = `/softalk/community/${communityId}/post/${postId}`;

    axios.get(url)
      .then(response => {
        setPostDetail(response.data);
      })
      .catch(error => {
        console.error('커뮤니티 내 게시글 상세 조회 중 오류 발생: ', error);
      });
  }, [communityId, postId]);

  if (!postDetail) {
    return <div>Loading...</div>;
  }

  return (
    <div className="post-detail">
      <h1>{postDetail.title}</h1>
      <p>작성자: {postDetail.writerName}</p>
      <p>작성일: {postDetail.postCreatedAt}</p>
      <p>수정일: {postDetail.postUpdatedAt}</p>
      <p>내용: {postDetail.content}</p>

      <h3>이미지 목록</h3>
      <ul>
        {postDetail.imageUrlList && postDetail.imageUrlList.map((imageUrl, index) => (
          <li key={index}>
            <img src={imageUrl} alt={`Image ${index + 1}`} />
          </li>
        ))}
      </ul>

      <h3>댓글 목록</h3>
      <ul>
        {postDetail.commentList && postDetail.commentList.map(comment => (
          <li key={comment.commentId}>
            <p>작성자: {comment.writerName}</p>
            <p>작성일: {comment.createdAt}</p>
            <p>수정일: {comment.updatedAt}</p>
            <p>내용: {comment.content}</p>
            <ul>
            {comment.children && comment.children.map(reply => (
                <li key={reply.commentId}>
                    <p>((대댓글))</p>
                    <p>작성자: {reply.writerName}</p>
                    <p>작성일: {reply.createdAt}</p>
                    <p>수정일: {reply.updatedAt}</p>
                    <p>내용: {reply.content}</p>
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
