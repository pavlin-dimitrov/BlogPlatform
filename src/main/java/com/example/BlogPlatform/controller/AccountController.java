package com.example.BlogPlatform.controller;

import com.example.BlogPlatform.entity.Account;
import com.example.BlogPlatform.entity.Post;
import com.example.BlogPlatform.services.contract.AccountService;
import com.example.BlogPlatform.services.contract.PostService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@SessionAttributes("auth")
public class AccountController {

  private final AccountService accountService;
  private final PostService postService;

  @GetMapping("/register")
  public String getRegisterPage(Model model) {
    model.addAttribute("registerRequest", new Account());
    return "register";
  }

  @PostMapping("/register")
  public String register(@ModelAttribute(value = "registerRequest") Account account) {
    Account registered = accountService.save(account.getName(), account.getBlogName(),
        account.getEmail(), account.getPassword());
    return registered == null ? "registration_error" : "redirect:/login";
  }

  @GetMapping("/login")
  public String getLoginPage(Model model) {
    model.addAttribute("loginRequest", new Account());
    return "login";
  }

  @PostMapping("/login")
  public String login(@ModelAttribute Account account, Model model, HttpSession session) {
    Account authenticated = accountService.authentication(account.getEmail(),
        account.getPassword());
    if (authenticated != null) {
      session.setAttribute("authId", authenticated.getId());
      session.setAttribute("auth", authenticated);
      return "redirect:/account";
    } else {
      return "authenticated_error";
    }
  }

  @GetMapping("/account")
  public void allAccounts(@ModelAttribute Account account, Model model, HttpSession session){
    Account auth = (Account) session.getAttribute("auth");
    List<Account> accounts = accountService.getAllAccounts();
    model.addAttribute("authenticated", auth);
    model.addAttribute("accounts", accounts);
  }

  @GetMapping("/public/{id}")
  public String openAccount(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
    Account auth = (Account) session.getAttribute("auth");
    Account account = accountService.getAccountById(id);
    List<Post> posts = postService.getAllByAccountId(id);
    model.addAttribute("posts", posts);
    model.addAttribute("account", account);
    model.addAttribute("authenticated", auth);

    if (account.getId()
        .equals(auth.getId())){ //session.getAttribute("authId")
      return "private_account";
    }
    return "public_account";
  }
}