package com.example.BlogPlatform.entity;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "posts")
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
  @Column(length = 1000)
  private String body;
  private String image;
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
  @Column(name = "delete_at")
  private LocalDateTime deletedAt;

  public Post(Account account, String title, String body, String image) {
    this.account = account;
    this.title = title;
    this.body = body;
    this.image = image;
  }

  public String getCreatedAt(LocalDateTime date){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy / MM / dd  -  HH:mm:ss");
    return date.format(formatter);
  }

  @Override
  public String toString() {
    return "Post{" +
        "id=" + id +
        ", account=" + account.getName() +
        ", title='" + title + '\'' +
        ", body='" + body + '\'' +
        ", image='" + image + '\'' +
        ", createdAt=" + createdAt +
        ", deletedAt=" + deletedAt +
        '}';
  }
}