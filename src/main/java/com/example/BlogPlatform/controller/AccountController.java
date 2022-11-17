package com.example.BlogPlatform.controller;

import com.example.BlogPlatform.entity.Account;
import com.example.BlogPlatform.services.contract.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AccountController {

  private final AccountService accountService;

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
    System.out.println("REGISTER: " + account);
    Account registered = accountService.registerAccount(account.getEmail(), account.getPassword());
    return registered == null ? "registration_error" : "redirect:/login";
  }
}
