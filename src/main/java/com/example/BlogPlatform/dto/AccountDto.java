package com.example.BlogPlatform.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class AccountDto {

  @NotNull
  private String email;
  @NotNull
  private String password;
}
