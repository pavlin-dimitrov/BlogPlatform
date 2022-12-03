package com.example.BlogPlatform.controller;

import com.example.BlogPlatform.entity.Account;
import com.example.BlogPlatform.entity.Post;
import com.example.BlogPlatform.services.contract.AccountService;
import com.example.BlogPlatform.services.contract.PostService;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
@SessionAttributes("auth")
public class PostController {

  @Autowired
  private final PostService postService;
  @Autowired
  private final AccountService accountService;

  @GetMapping("/new_post/{id}")
  public String newPost(@PathVariable Long id, Model model, HttpSession session) {
    Account authenticated = accountService.getAccountById(id);
    Post post = new Post();
    post.setAccount(authenticated);
    model.addAttribute("newpost", post);
    return "new_post";
  }

  @PostMapping("/save")
  public String save(@RequestParam("image_upload") MultipartFile image, @ModelAttribute Post post) {
    String imageName = image.getOriginalFilename();
    postService.uploadImage(image, post);
    post.setImage("/images/" + post.getAccount().getId() + "/" + imageName);
    postService.save(post);
    return "redirect:/post/" + post.getId();
  }

  @GetMapping("/post/{id}")
  public String getPostById(@PathVariable Long id, Model model, HttpSession session) {
    Optional<Post> optionalPost = this.postService.getById(id);
    Account auth = (Account) session.getAttribute("auth");
    model.addAttribute("authenticated", auth);

    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      model.addAttribute("post", post);
      return "post";
    } else {
      return "error404";
    }
  }

  @GetMapping("/edit/{id}")
  public String getPostForEdit(@PathVariable Long id, Model model, HttpSession session) {
    Account auth = (Account) session.getAttribute("auth");
    model.addAttribute("authenticated", auth);
    Optional<Post> optionalPost = this.postService.getById(id);
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      model.addAttribute("post", post);
      return "update";
    }
    return "redirect:/error404";
  }

  @PostMapping("/update/{id}")
  public String updatePost(@PathVariable Long id, @ModelAttribute("post") Post post,
      @RequestParam("image_upload") MultipartFile image) {
    String imageName = image.getOriginalFilename();
    String newImagePath = "/images/" + post.getAccount().getId() + "/" + imageName;
    postService.updatePost(id, post.getTitle(), post.getBody(), newImagePath, image);
    return "redirect:/post/" + post.getId();
  }

  @GetMapping("post/{id}/delete")
  public String deletePost(@PathVariable Long id, Model model) {
    Optional<Post> optionalPost = this.postService.getById(id);

    if (optionalPost.isPresent()) {
      Post postToDelete = optionalPost.get();
      model.addAttribute("post_to_delete", postToDelete);
      return "delete";
    } else {
      return "error404";
    }
  }

  @PostMapping("/delete/{id}")
  public String setDeletedAt(@ModelAttribute("post_to_delete") Post post){
    postService.deletePost(post.getId());
    return "redirect:/account";
  }
}