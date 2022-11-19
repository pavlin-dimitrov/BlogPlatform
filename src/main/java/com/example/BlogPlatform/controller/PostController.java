package com.example.BlogPlatform.controller;

import com.example.BlogPlatform.entity.Post;
import com.example.BlogPlatform.services.contract.PostService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class PostController {

  @Autowired
  private final PostService postService;

  @PostMapping("/posts")
  public String getAllPosts(Model model){
    List<Post> posts = postService.getAllPostsInDescOrder();
    model.addAttribute("posts", posts);
    return "public_account";
  }

  @GetMapping("/post/{id}")
  public String getPostById(@PathVariable Long id, Model model){
    Optional<Post> optionalPost = this.postService.getById(id);

    if (optionalPost.isPresent()){
      Post post = optionalPost.get();
      model.addAttribute("post", post);
      return "post";
    } else
      return "error404";
  }

  @GetMapping("/update/{id}") //TODO finish this method
  public String updatePost(@PathVariable Long id, Model model){
    Optional<Post> optionalPost = this.postService.getById(id);
    return "redirect:/posts";
  }

  @GetMapping("/delete/{id}")
  public String deletePost(@PathVariable Long id){
    Optional<Post> optionalPost = this.postService.getById(id);

    if (optionalPost.isPresent()){
      postService.deletePost(id);
      return "redirect:/"; //TODO How to redirect to my page?
    } else
      return "error404";
  }
}
