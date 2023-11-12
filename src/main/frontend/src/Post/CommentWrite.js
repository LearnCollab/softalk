import React, { useState } from 'react';
import axios from 'axios';
import { useLocation, useParams, useNavigate } from 'react-router-dom';

const CommentWrite = ({ postId, parentCommentId }) => {
  const { state } = useLocation();
  const { communityId } = useParams();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    token: state ? state.token : '',
    content: state ? state.content : '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const createComment = (e) => {
    e.preventDefault();

    const url = `/softalk/comment`;

    const token = localStorage.getItem('jwt');

    const headers = {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    };

    const data = {
      content: formData.content,
      parentCommentId: parentCommentId,
      postId: postId
    };

    axios.post(url, data, { headers })
      .then(response => {
        if (response.status === 200) {
          alert('댓글 작성 성공');
          window.location.reload();
        } else {
          alert('댓글 작성 실패');
        }
      })
      .catch(error => {
        console.error('댓글 작성 중 오류 발생:', error);
      });
  };

  return (
    <div className="comment-write">
      <form onSubmit={createComment}>
        <div>
          <label>{formData.parentCommentId == null ? '댓글' : '대댓글'}</label>
          <textarea
            name="content"
            value={formData.content}
            onChange={handleChange}
            placeholder="댓글 내용을 입력하세요."
          />
          <button type="submit">작성</button>
        </div>
      </form>
    </div>
  );
};

export default CommentWrite;
