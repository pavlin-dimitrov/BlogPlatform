package com.example.BlogPlatform.controller;

import com.example.BlogPlatform.entity.Account;
import com.example.BlogPlatform.entity.Post;
import com.example.BlogPlatform.services.contract.AccountService;
import com.example.BlogPlatform.services.contract.PostService;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AccountController {

  private final AccountService accountService;
  private final PostService postService;

  @GetMapping("/register")
  public String getRegisterPage(Model model) {
    model.addAttribute("registerRequest", new Account());
    return "register";
  }

  @GetMapping("/login")
  public String getLoginPage(Model model) {
    model.addAttribute("loginRequest", new Account());
    return "login";
  }

  @PostMapping("/register")
  public String register(@ModelAttribute Account account) {
    Account registered = accountService.save(account.getName(), account.getBlogName(),
        account.getEmail(), account.getPassword());
    return registered == null ? "registration_error" : "redirect:/login";
  }

  @PostMapping("/login")
  //TODO Check if list of accounts can be outside of this method ?
  public String login(@ModelAttribute Account account, Model model) {
    Account authenticated = accountService.authentication(account.getEmail(),
        account.getPassword());
    if (authenticated != null) {
      List<Account> accounts = accountService.getAllAccounts();
      model.addAttribute("accounts", accounts);
      return "account";
    } else {
      return "authenticated_error";
    }
  }

  @GetMapping("/public/{id}")
  public String openPublicAccount(@PathVariable(value = "id") Long id, Model model) {
    Account account = accountService.getAccountById(id);
    List<Post> posts = postService.getAllByAccountId(id);
//
//    for (int i = 0; i < accountPosts.size(); i++) {
//      System.out.println(accountPosts.get(i).toString());
//    }
    model.addAttribute("posts", posts);
    model.addAttribute("account", account);
    return "public_account";
  }

//  @GetMapping("/public/{id}/posts")
//  public String loadAllPostsPerAccount(@PathVariable(value = "id") Long id, Model model) {
//    List<Post> accountPosts = postService.getAllByAccountId(id);
//    model.addAttribute("accountPost", accountPosts);
//    return "public_account";
//  }
}
