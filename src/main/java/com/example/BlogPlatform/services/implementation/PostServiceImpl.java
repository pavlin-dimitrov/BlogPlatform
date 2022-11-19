package com.example.BlogPlatform.services.implementation;

import com.example.BlogPlatform.entity.Post;
import com.example.BlogPlatform.repository.PostRepository;
import com.example.BlogPlatform.services.contract.PostService;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

  @Autowired
  private final PostRepository postRepository;

  @Override
  public List<Post> getAll() {
    return postRepository.findAll();
  }

  @Override
  public List<Post> getAllByAccountId(Long id) {
    List<Post> accountPosts = postRepository.findAllByAccountId(id);
    return accountPosts.stream().filter(post -> post.getDeletedAt() == null)
        .collect(Collectors.toList());
  }

  public List<Post> getAllPostsInDescOrder() {
    return postRepository.findAllByOrderByCreatedAtDesc().stream()
        .filter(post -> post.getDeletedAt() == null)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Post> getById(Long id) {
    return postRepository.findById(id);
  }

  @Override
  public Post save(Post post) {
    if (post.getId() == null) {
      post.setCreatedAt(LocalDate.now());
    }
    return postRepository.save(post);
  }

  @Override
  public void deletePost(Long postId) {
    Optional<Post> optionalPost = postRepository.findById(postId);
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      post.setDeletedAt(LocalDate.now());
    }
  }

  //TODO may move this logic to the Controller and here just use the save() method;
  @Override
  public void updatePost(Long postId, String title, String body, String imageURL) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalStateException("Post with ID: " + postId + " does not exists!"));
    if (title != null && title.length() > 0 && !Objects.equals(post.getTitle(), title)) {
      post.setTitle(title);
    }
    if (body != null && body.length() > 0 && !Objects.equals(post.getBody(), body)) {
      post.setBody(body);
    }
    if (imageURL != null && imageURL.length() > 0 && !Objects.equals(post.getBody(), imageURL)) {
      post.setImage(imageURL);
    }
  }

}
