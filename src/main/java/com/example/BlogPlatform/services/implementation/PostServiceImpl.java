package com.example.BlogPlatform.services.implementation;

import com.example.BlogPlatform.entity.Post;
import com.example.BlogPlatform.repository.PostRepository;
import com.example.BlogPlatform.services.contract.PostService;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

  public static final String IMAGE_STORAGE =
      System.getProperty("user.dir").
          replace("\\", "/") + "/src/main/resources/static/images/";
  public static final String OLD_IMAGE_PATH =
      System.getProperty("user.dir").
          replace("\\", "/") + "/src/main/resources/static";
  @Autowired
  private final PostRepository postRepository;

  @Override
  public List<Post> getAll() {
    return postRepository.findAll();
  }

  @Override
  public List<Post> getAllByAccountId(Long id) {
    List<Post> accountPosts = postRepository.findAllByAccountIdOrderByCreatedAtDesc(id);
    return accountPosts.stream().filter(post -> post.getDeletedAt() == null)
        .collect(Collectors.toList());
  }

  @Override
  public List<Post> getAllPostsInDescOrder() {
    return postRepository.findAllByOrderByCreatedAtDesc()
        .stream()
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
      post.setCreatedAt(LocalDateTime.now());
    }
    return postRepository.save(post);
  }

  @Override
  public void uploadImage(MultipartFile image, Post post) {
    String imageName = image.getOriginalFilename();
    try {
      image.transferTo(new File(IMAGE_STORAGE + post.getAccount().getId() + "/" + imageName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deletePost(Long postId) {
    Optional<Post> optionalPost = postRepository.findById(postId);
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      post.setDeletedAt(LocalDateTime.now());
    }
  }

  @Override
  @Transactional
  public void updatePost(Long postId, String title, String body, String newImageURL,
      MultipartFile newImageFile) {

    Post post = postRepository.findById(postId).orElseThrow(
        () -> new IllegalStateException("Post with ID: " + postId + " does not exists!"));

    if (title != null && title.length() > 0 && !Objects.equals(post.getTitle(), title)) {
      post.setTitle(title);
    }

    if (body != null && body.length() > 0 && !Objects.equals(post.getBody(), body)) {
      post.setBody(body);
    }

    if (newImageFile.getOriginalFilename() != null
        && newImageFile.getOriginalFilename().length() > 0) {
      uploadImage(newImageFile, post);
      deleteOldImageFile(post);
      post.setImage(newImageURL);
    }
    postRepository.save(post);
  }

  @Override
  public void deleteOldImageFile(Post post) {
    File imageToDelete = new File(getOldImagePath(post));
    imageToDelete.delete();
  }

  @Override
  public String getOldImagePath(Post post) {
    System.out.println();
    System.out.println("IMAGE TO DELETE PATH: " + OLD_IMAGE_PATH + post.getImage());
    System.out.println();
    System.out.println();
    return OLD_IMAGE_PATH + post.getImage();
  }
}