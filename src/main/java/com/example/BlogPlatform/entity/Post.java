package com.example.BlogPlatform.entity;

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
import org.hibernate.annotations.Cascade;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(targetEntity = User.class)
  @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
  @JoinColumn(name = "user_id")
  private User user;

  private String title;
  private String body;
  private String image;
  @Column(name = "posting_date", nullable = false)
  private Date dateOfPosting;

  @Column(name = "delete_at")
  private Date deletedAt;

  public Post(User user, String title, String body, String image, Date dateOfPosting, Date deletedAt) {
    this.user = user;
    this.title = title;
    this.body = body;
    this.image = image;
    this.dateOfPosting = dateOfPosting;
    this.deletedAt = deletedAt;
  }
}