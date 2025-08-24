package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service.impl;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.Blog;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.User;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository.BlogRepository;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository.UserRepository;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsernameOrEmail(auth.getName(), auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    @Override
    public Blog createBlog(Blog blog) {
        User currentUser = getCurrentUser();
        blog.setAuthor(currentUser);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setLikes(new HashSet<>());
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog updatedBlog) {
        Blog blog = getBlogById(id);
        User currentUser = getCurrentUser();

        if (!blog.getAuthor().getId().equals(currentUser.getId()) &&
                currentUser.getRoles().stream().noneMatch(r -> r.getName().equals("ROLE_ADMIN"))) {
            throw new RuntimeException("You are not allowed to edit this blog");
        }

        blog.setTitle(updatedBlog.getTitle());
        blog.setContent(updatedBlog.getContent());
        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Long id) {
        Blog blog = getBlogById(id);
        User currentUser = getCurrentUser();

        if (!blog.getAuthor().getId().equals(currentUser.getId()) &&
                currentUser.getRoles().stream().noneMatch(r -> r.getName().equals("ROLE_ADMIN"))) {
            throw new RuntimeException("You are not allowed to delete this blog");
        }
        blog.getLikes().clear();
        blogRepository.delete(blog);
    }

    @Override
    public Blog toggleLike(Long blogId) {
        Blog blog = getBlogById(blogId);
        User currentUser = getCurrentUser();

        if (blog.getLikes().contains(currentUser)) {
            blog.getLikes().remove(currentUser);
        } else {
            blog.getLikes().add(currentUser);
        }

        return blogRepository.save(blog);
    }
}