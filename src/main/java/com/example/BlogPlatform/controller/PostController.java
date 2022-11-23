package com.example.BlogPlatform.controller;

import com.example.BlogPlatform.entity.Account;
import com.example.BlogPlatform.entity.Post;
import com.example.BlogPlatform.services.contract.AccountService;
import com.example.BlogPlatform.services.contract.PostService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class PostController {

  @Autowired
  private final PostService postService;
  @Autowired
  private final AccountService accountService;
  public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/images";

  @GetMapping("/new_post/{id}")
  public String newPost(@PathVariable Long id, Model model) {
    Account authenticated = accountService.getAccountById(id);
    Post post = new Post();
    post.setAccount(authenticated);
    model.addAttribute("newpost", post);
    return "new_post";
  }

  @PostMapping("/save")
  public String save(@ModelAttribute Post post,
      @RequestParam("image") MultipartFile multipartFile) {
    postService.save(post);
    return "redirect:/post/" + post.getId();
  }


  @GetMapping("/post/{id}")
  public String getPostById(@PathVariable Long id, Model model) {
    Optional<Post> optionalPost = this.postService.getById(id);

    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      model.addAttribute("post", post);
      return "post";
    } else {
      return "error404";
    }
  }

  @GetMapping("/update/{id}") //TODO finish this method
  public String updatePost(@PathVariable Long id, Model model) {
    Optional<Post> optionalPost = this.postService.getById(id);
    return "redirect:/posts";
  }

  @GetMapping("/delete/{id}")
  public String deletePost(@PathVariable Long id) {
    Optional<Post> optionalPost = this.postService.getById(id);

    if (optionalPost.isPresent()) {
      postService.deletePost(id);
      return "redirect:/"; //TODO How to redirect to my page?
    } else {
      return "error404";
    }
  }
}
