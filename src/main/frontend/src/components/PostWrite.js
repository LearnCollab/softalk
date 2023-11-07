import React, { useState } from 'react';
import axios from 'axios';
import { useParams, Link, useNavigate } from 'react-router-dom';

const PostWrite = () => {
  const navigate = useNavigate();

  const { communityId } = useParams();
  const [formData, setFormData] = useState({
    token: '',
    title: '',
    content: '',
    images: []
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  }

  const handleImageChange = (e) => {
    const files = e.target.files;
    setFormData({
      ...formData,
      images: files
    });
  }

  const createPost = (e) => {
    e.preventDefault();

    const headers = {
      'Authorization': `Bearer ${formData.token}`,
      'Content-Type': 'multipart/form-data'
    };

    const url = `/softalk/community/${communityId}`;
    const { title, content, images } = formData;

    if (!content.trim()) {
       alert('내용을 입력해 주세요.');
       return;
    }

    const postData = new FormData();
    const data = {
        title: title || '',
        content
    };

    const json = JSON.stringify(data);
    const blob = new Blob([json], { type: "application/json" });
    postData.append("data", blob);

    for (let i = 0; i < images.length; i++) {
        postData.append(`imageList`, images[i]);
    }

    axios.post(url, postData, { headers })
      .then(response => {
        if (response.status === 200) {
          alert('게시글 작성 성공');
          navigate(`/softalk/community/${communityId}`);
        } else {
          alert('게시글 작성 실패');
        }
      })
      .catch(error => {
        console.error('게시글 작성 중 오류 발생:', error);
      });
  }

  return (
    <div className="post-create">
      <h1>게시글 작성</h1>
      <form onSubmit={createPost}>
        <div>
          <label>토큰</label>
          <input
            type="text"
            name="token"
            value={formData.token}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>제목</label>
          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>내용</label>
          <textarea
            name="content"
            value={formData.content}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>이미지 업로드</label>
          <input
            type="file"
            accept="image/*"
            multiple
            onChange={handleImageChange}
          />
        </div>
        <button type="submit">작성하기</button>
      </form>
      <Link to={`/softalk/community/${communityId}`}>게시글 목록으로 돌아가기</Link>
    </div>
  );
}

export default PostWrite;
