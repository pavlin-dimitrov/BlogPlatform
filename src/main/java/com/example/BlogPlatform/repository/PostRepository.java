package com.example.BlogPlatform.repository;

import com.example.BlogPlatform.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  <Optional>Post getPostById(Long id);

  List<Post> findAllByOrderByCreatedAdDesc();
}
