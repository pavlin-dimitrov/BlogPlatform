package com.example.BlogPlatform.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
public class Account implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "blog_name", nullable = false)
  private String blogName;
  @Column(name = "email", nullable = false)
  private String email;
  @Column(name = "password", nullable = false)
  private String password;
  @OneToMany(mappedBy = "account")
  private List<Post> posts;

  public Account(String name, String blogName, String email, String password) {
    this.name = name;
    this.blogName = blogName;
    this.email = email;
    this.password = password;
  }
}