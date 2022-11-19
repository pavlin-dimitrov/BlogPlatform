package com.example.BlogPlatform.services.contract;

import com.example.BlogPlatform.entity.Account;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

  Account save(String name, String blogName, String email, String password);

  Account authentication(String email, String password);

  List<Account> getAllAccounts();

  Account getAccountById(Long id);
}
