import axios from 'axios';
import React, { useState } from 'react';

function CreateCommunity() {
  const [community, setCommunity] = useState({
    cm_name: '',
    cm_type: '',
    members_limit: '',
    category: '',
  });
  const [images, setImages] = useState([]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const parsedValue = parseInt(value, 10);
    setCommunity({
      ...community,
      [name]: isNaN(parsedValue) ? value : parsedValue,
    });
  };

  const handleImageChange = (e) => {
    setImages(e.target.files);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    for (let i = 0; i < images.length; i++) {
      formData.append('image', images[i]);
    }
    formData.append('communityDto', new Blob([JSON.stringify(community)], {
      type: 'application/json',
    }));

    const token = localStorage.getItem('jwt'); // 토큰을 localStorage에서 가져오기
    if (token) {
      console.log(token);
      // axios 호출
    } else {
      console.log('토큰이 없습니다.');
    }

    try {
      await axios.post('/softalk/community', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${token}`, // 토큰을 헤더에 추가
        },
      });
      alert('커뮤니티가 성공적으로 생성되었습니다.');
    } catch (error) {
      console.error('커뮤니티 생성에 실패했습니다.', error);
      alert('커뮤니티 생성에 실패했습니다.');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h1>Create Community</h1>
      <label>
        Title:
        <input
          type="text"
          name="cm_name"
          value={community.title}
          onChange={handleChange}
          required
        />
      </label>
      <br />
      <label>
        Community Type:
        <select
          name="cm_type"
          value={community.cm_type}
          onChange={handleChange}
          required
        >
          <option value="">커뮤니티 타입을 선택하세요</option>
          <option value="0">선착순</option>
          <option value="1">신청서 제공</option>
        </select>
      </label>
      <br />
      <label>
        Members Limit:
        <select
          name="members_limit"
          value={community.members_limit}
          onChange={handleChange}
          required
        >
          <option value="">최대 인원수를 선택하세요</option>
          <option value="10">10</option>
          <option value="20">20</option>
        </select>
      </label>
      <br />
      <label>
        Category:
        <select
          name="category"
          value={community.category}
          onChange={handleChange}
          required
        >
          <option value="">카테고리를 선택하세요</option>
          <option value="0">스터디</option>
          <option value="1">친목</option>
        </select>
      </label>
      <br />
      <label>
        Images:
        <input type="file" multiple onChange={handleImageChange} />
      </label>
      <br />
      <button type="submit">Create</button>
    </form>
  );
}

export default CreateCommunity;
