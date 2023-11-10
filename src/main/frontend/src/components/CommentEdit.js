import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useLocation } from 'react-router-dom';

const CommentEdit = () => {

    const { commentId } = useParams();
    const location = useLocation();
    const token = location.state ? location.state.token : '';

    useEffect(() => {
        const url = `/softalk/comment/${commentId}`;

        axios.delete(url, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
        .then(response => {
            if(response.status == 200){
                alert('댓글 삭제 성공');
            }
        })
        .catch(error => {
            console.error('댓글 삭제 중 오류 발생: ', error);
        });
    }, [commentId, token]);

    return null;
}

export default CommentEdit;