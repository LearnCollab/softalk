import React, { useState } from 'react';
import axios from 'axios';
import { useLocation, useParams, useNavigate } from 'react-router-dom';
import { Button } from 'react-bootstrap';

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
        alert('댓글 작성 중 오류 발생');
        console.error('댓글 작성 중 오류 발생:', error);
      });
  };

  const token = localStorage.getItem('jwt');

  return (

    <div className="reply-form">

        {
            token
            ?
            <form onSubmit={createComment}>
                <div className="row">
                    <div className="col form-group">
                        <label>{formData.parentCommentId == null ? '댓글' : '대댓글'}</label>
                        <textarea name="content"
                            value={formData.content}
                            onChange={handleChange}
                            className="form-control"
                            placeholder="댓글을 입력해 주세요.">
                        </textarea>
                    </div>
                </div>
                <button type="submit" className="btn btn-primary">작성</button>
            </form>
            :
            <div>
            <p>댓글 작성은 로그인 후 가능합니다.</p>
            <button onClick={() => navigate('/auth/login')} className="btn btn-primary">로그인 하러가기</button>
            </div>
        }

    </div>
  );
};

export default CommentWrite;
