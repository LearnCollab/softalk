import React, { useState } from 'react';
import axios from 'axios';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { Breadcrumb, Form, Button, Container, Row, Col, ButtonGroup } from 'react-bootstrap';

const PostWrite = () => {
  const navigate = useNavigate();

  const { communityId } = useParams();
  const [formData, setFormData] = useState({
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

  const createPost = async (e) => {
    e.preventDefault();

    const token = localStorage.getItem('jwt');
    const headers = {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'multipart/form-data'
    };

    const url = `/softalk/community/${communityId}`;
    const { title, content, images } = formData;

    if (!content.trim()) {
       alert('내용을 입력해 주세요.');
       return;
    }

    const postData = new FormData();
    const data = JSON.stringify({
        title: formData.title || '',
        content: formData.content
    });

    const blob = new Blob([data], { type: "application/json" });
    postData.append("data", blob);

    for (let i = 0; i < formData.images.length; i++) {
        postData.append(`imageList`, formData.images[i]);
    }

    try {
        await axios.post(url, postData, { headers });
        alert('게시글 작성 성공');
        navigate(`/softalk/community/${communityId}`);
    } catch (error) {
        console.error('게시글 작성 중 오류 발생: ', error);

        const statusCode = error.response.status;
        if(statusCode == 500){
            alert('게시글 작성 중 문제가 발생했습니다: 서버 내부 오류');
        }else{
            alert('게시글 작성 중 문제가 발생했습니다.');
        }
    }

  }

  return (
    <main id="main">
        <div className="breadcrumbs">
            <div className="container">
                <div className="d-flex justify-content-between align-items-center">
                    <h2>샘플 커뮤니티</h2>
                    <ol>
                        <li><Link to={`/`}>Home</Link></li>
                        <li><Link to={`/softalk/community/${communityId}`}>샘플 커뮤니티</Link></li>
                        <li>게시글 작성</li>
                    </ol>
                </div>
            </div>
        </div>

        <Container className="post-create">
            <Row>
                <Col>
                    <h3 style={{ marginBottom: '25px', marginTop: '40px' }}>게시글 작성</h3>
                    <Form onSubmit={createPost}>
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
                        <Form.Group controlId="formPostImages" style={{ marginBottom: '15px' }}>
                            <Form.Label style={{ fontWeight: 'bold' }}>이미지 업로드</Form.Label><br/>
                            <input
                                type="file"
                                multiple
                                accept="image/*"
                                onChange={handleImageChange}
                            />
                        </Form.Group>
                        <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '40px' }}>
                            <Button variant="primary" type="submit">작성</Button>
                            <Link to={`/softalk/community/${communityId}`}>
                                <Button variant="secondary" type="submit" style={{ marginLeft: '7px' }}>취소</Button>
                            </Link>
                        </div>
                    </Form>
                </Col>
            </Row>
        </Container>
    </main>
  );
}

export default PostWrite;
