package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.Comment;

public interface CommentService {
    Comment addComment(Long blogId, Comment comment);
}
