package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.Blog;
import java.util.List;

public interface BlogService {
    List<Blog> getAllBlogs();
    Blog getBlogById(Long id);
    Blog createBlog(Blog blog);
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);
    Blog toggleLike(Long blogId);

}
