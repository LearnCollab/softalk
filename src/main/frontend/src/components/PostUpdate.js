import React, { useState } from 'react';
import axios from 'axios';
import { useParams, Link, useNavigate, useLocation } from 'react-router-dom';

const PostUpdate = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { communityId, postId } = useParams();

  const postDetail = location.state && location.state.postDetail
    ? location.state.postDetail
    : { title: '', content: '' };

  const [formData, setFormData] = useState({
    token: '',
    title: postDetail ? postDetail.title : '',
    content: postDetail ? postDetail.content : '',
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

  const goToPostDetail = () => {
    navigate(`/softalk/community/${communityId}/post/${postId}`);
  }

  const updatePost = (e) => {
    e.preventDefault();

    const headers = {
      'Authorization': `Bearer ${formData.token}`,
      'Content-Type': 'multipart/form-data'
    };

    const url = `/softalk/community/${communityId}/post/${postId}`;

    const postData = new FormData();
    const data = {
        title: formData.title || '',
        content: formData.content
    };

    const json = JSON.stringify(data);
    const blob = new Blob([json], { type: "application/json" });
    postData.append("data", blob);

    for (let i = 0; i < formData.images.length; i++) {
        postData.append(`imageList`, formData.images[i]);
    }

    axios.put(url, postData, { headers })
      .then(response => {
        if (response.status === 200) {
          alert('게시글 수정 성공');
          goToPostDetail();
        } else {
          alert('게시글 수정 실패');
        }
      })
      .catch(error => {
        if(error.response.state == 403){
            alert('게시글 수정 불가: 게시글 수정 권한 없음');
        }
        console.error('게시글 수정 중 오류 발생:', error);
      });
  }

  return (
    <div className="post-update">
      <h1>게시글 수정</h1>
      <form onSubmit={updatePost}>
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
            <h3>이미지 목록</h3>
             <ul>
                {postDetail.imageUrlList && postDetail.imageUrlList.map((imageUrl, index) => (
                    <li key={index}>
                        <img
                            src={imageUrl}
                            alt={`Image ${index + 1}`}
                            style={{ maxWidth: '200px', maxHeight: '200px' }}/>
                    </li>
                ))}
             </ul>
        </div>

        <div>
            <h3>이미지 추가 업로드</h3>
            <input
                type="file"
                accept="image/*"
                multiple
                onChange={handleImageChange}
            />
        </div>

        <button type="submit">수정하기</button>
        <button type="button" onClick={goToPostDetail}>취소</button>
      </form>
    </div>
  );
}

export default PostUpdate;