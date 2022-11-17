package com.example.BlogPlatform.entity;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotNull
  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
  private Account account;
  private String title;
  private String body;
  private String image;
  @Column(name = "created_at", nullable = false)
  private LocalDate createdAt;
  @Column(name = "delete_at")
  private LocalDate deletedAt;

  public Post(Account account, String title, String body, String image) {
    this.account = account;
    this.title = title;
    this.body = body;
    this.image = image;
  }
}