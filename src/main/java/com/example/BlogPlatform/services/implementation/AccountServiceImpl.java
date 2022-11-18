package com.example.BlogPlatform.services.implementation;

import com.example.BlogPlatform.entity.Account;
import com.example.BlogPlatform.repository.AccountRepository;
import com.example.BlogPlatform.services.contract.AccountService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

  @Autowired
  private final AccountRepository accountRepository;

  @Override
  public Account save(String name, String blogName, String email, String password) {
    Optional<Account> accountByEmail = accountRepository.findAccountByEmailContainsIgnoreCase(email);
    if (accountByEmail.isPresent()) {
      return null;
    } else {
      Account account = new Account(name, blogName, email, password);
      return accountRepository.save(account);
    }
  }

  @Override
  public Account authentication(String email, String password){
    return accountRepository.findAccountByEmailAndPassword(email, password)
        .orElseThrow(() -> new IllegalStateException("Account does not exists!"));
  }

}
