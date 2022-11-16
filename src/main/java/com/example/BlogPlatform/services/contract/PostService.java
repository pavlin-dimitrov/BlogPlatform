package com.example.BlogPlatform.services.contract;

import com.example.BlogPlatform.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

  List<Post> getAllPostsInDescOrder();

  Optional<Post> getById(Long id);

  Post save(Post post);

  void deletePost(Long postId);

  void updatePost(Long postId, String title, String body, String imageURL);
}
