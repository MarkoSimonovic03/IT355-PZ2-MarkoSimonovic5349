package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {}