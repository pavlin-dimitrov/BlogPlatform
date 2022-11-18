package com.example.BlogPlatform.repository;

import com.example.BlogPlatform.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findAccountByEmailAndPassword(String email, String password);

  Optional<Account> findAccountByEmailContainsIgnoreCase(String email);
}
