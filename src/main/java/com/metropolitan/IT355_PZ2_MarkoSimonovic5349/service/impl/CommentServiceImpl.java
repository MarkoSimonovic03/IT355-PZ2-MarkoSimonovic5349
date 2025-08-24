package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service.impl;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.Blog;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.Comment;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.User;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository.BlogRepository;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository.CommentRepository;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository.UserRepository;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsernameOrEmail(auth.getName(), auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Comment addComment(Long blogId, Comment comment) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        User currentUser = getCurrentUser();

        Comment cmmnt = new Comment();
        cmmnt.setBlog(blog);
        cmmnt.setUser(currentUser);
        cmmnt.setContent(comment.getContent());

        return commentRepository.save(cmmnt);
    }
}