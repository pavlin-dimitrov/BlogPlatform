package com.example.BlogPlatform.services.implementation;

import com.example.BlogPlatform.entity.Account;
import com.example.BlogPlatform.repository.AccountRepository;
import com.example.BlogPlatform.services.contract.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

  @Autowired
  private final AccountRepository accountRepository;

  @Override
  public Account registerAccount(String email, String password) {
    if (email == null && password == null) {
      return null;
    } else {
      Account account = new Account(email, password);
      return accountRepository.save(account);
    }
  }

  @Override
  public Account authentication(String email, String password){
    return accountRepository.findAccountByEmailAndPassword(email, password)
        .orElseThrow(() -> new IllegalStateException("Account does not exists!"));
  }

}
