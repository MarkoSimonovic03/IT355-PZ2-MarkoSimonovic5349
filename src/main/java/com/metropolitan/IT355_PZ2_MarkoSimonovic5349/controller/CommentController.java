package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.controller;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.Comment;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/blog/{blogId}")
    public Comment addComment(@PathVariable Long blogId, @RequestBody Comment comment) {
        return commentService.addComment(blogId, comment);
    }
}
