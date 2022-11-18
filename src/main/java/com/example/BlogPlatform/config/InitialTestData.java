package com.example.BlogPlatform.config;

import com.example.BlogPlatform.entity.Account;
import com.example.BlogPlatform.entity.Post;
import com.example.BlogPlatform.repository.AccountRepository;
import com.example.BlogPlatform.repository.PostRepository;
import com.example.BlogPlatform.services.contract.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialTestData implements CommandLineRunner {

  @Autowired
  AccountRepository accountRepository;
  @Autowired
  PostRepository postRepository;
  @Autowired
  PostService postService;

  @Override
  public void run(String... args) throws Exception {

      Account account1 = new Account();
      account1.setName("Elon Musk");
      account1.setBlogName("SpaceX Blogger");
      account1.setEmail("accountOne@abv.bg");
      account1.setPassword("accountOne1");

      Account account2 = new Account();
      account2.setName("Jeff Bezos");
      account2.setBlogName("Blue Origin Space Blog");
      account2.setEmail("accountTwo@abv.bg");
      account2.setPassword("accountTwo2");

      accountRepository.save(account1);
      accountRepository.save(account2);

      Post post1 = new Post();
      post1.setAccount(account1);
      post1.setTitle("Post number One");
      post1.setBody("This is the body of post number one");
      post1.setImage("URL 1 ot image");

      Post post2 = new Post();
      post2.setAccount(account2);
      post2.setTitle("Post number Two");
      post2.setBody("This is the body of post number TWO");
      post2.setImage("URL 2 ot image");
      postService.save(post1);
      postService.save(post2);
  }
}
