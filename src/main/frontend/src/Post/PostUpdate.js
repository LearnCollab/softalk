import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import { Breadcrumb, Container, Row, Col, Form, Button } from 'react-bootstrap';

const PostUpdate = () => {
  const navigate = useNavigate();
  const { communityId, postId } = useParams();
  const [formData, setFormData] = useState({
    title: '',
    content: '',
    images: []
  });

  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await axios.get(`/softalk/community/${communityId}/post/${postId}`);
        setFormData({
          title: response.data.title,
          content: response.data.content,
          images: response.data.images
        });
        // 로드된 이미지를 처리하는 로직 추가 필요
      } catch (error) {
        console.error('게시글 정보 로드 중 오류 발생: ', error);
      }
    };

    fetchPost();
  }, [communityId, postId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

//  const handleImageChange = (e) => {
//    const files = e.target.files;
//    setFormData({
//      ...formData,
//      images: files
//    });
//  };

  const updatePost = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('jwt');
    const headers = {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'multipart/form-data'
    };

    const postData = new FormData();
    const data = JSON.stringify({
      title: formData.title || '',
      content: formData.content
    });

    const blob = new Blob([data], { type: "application/json" });
    postData.append("data", blob);

//    for (let i = 0; i < formData.images.length; i++) {
//      postData.append(`imageList`, formData.images[i]);
//    }

    try {
      await axios.put(`/softalk/community/${communityId}/post/${postId}`, postData, { headers });
      alert('게시글 수정 성공');
      navigate(`/softalk/community/${communityId}/post/${postId}`);
    } catch (error) {
      console.error('게시글 수정 중 오류 발생: ', error);

      const statusCode = error.response.status;
      if(statusCode == 403){
        alert('게시글 수정 중 문제가 발생했습니다: 게시글 수정 권한 없음');
      }else if(statusCode == 500){
        alert('게시글 수정 중 문제가 발생했습니다: 서버 내부 오류');
      }else{
        alert('게시글 수정 중 문제가 발생했습니다.');
      }
    }
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
                    <li>게시글 수정</li>
                </ol>
            </div>
          </div>
      </div>

      <Container className="post-create">
        <Row>
          <Col>
            <h3 style={{ marginTop: '40px' }}>게시글 수정</h3>
            <div style={{ marginBottom: '25px' }}>게시글의 제목과 내용만 수정 가능합니다.</div>

            <Form onSubmit={updatePost}>
              <Form.Group controlId="formPostTitle" style={{ marginBottom: '15px' }}>
                <Form.Label style={{ fontWeight: 'bold' }}>제목</Form.Label>
                <Form.Control
                  type="text"
                  name="title"
                  value={formData.title}
                  onChange={handleChange}
                  placeholder="제목을 입력하세요" />
              </Form.Group>
              <Form.Group controlId="formPostContent" style={{ marginBottom: '15px' }}>
                <Form.Label style={{ fontWeight: 'bold' }}>내용</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={5}
                  name="content"
                  value={formData.content}
                  onChange={handleChange}
                  placeholder="내용을 입력하세요" />
              </Form.Group>
            </Form>

            <div style={{ display: 'flex', justifyContent: 'flex-end', marginTop: '30px', marginBottom: '30px' }}>
                <Button variant="primary" onClick={updatePost}>수정</Button>
                <Link to={`/softalk/community/${communityId}`}>
                  <Button variant="secondary" style={{ marginLeft: '7px' }}>취소</Button>
                </Link>
            </div>

          </Col>
        </Row>
      </Container>
    </main>
  );

};

export default PostUpdate;