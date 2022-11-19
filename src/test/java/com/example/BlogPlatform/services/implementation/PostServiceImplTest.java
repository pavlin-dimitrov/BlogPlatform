package com.example.BlogPlatform.services.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.BlogPlatform.entity.Account;
import com.example.BlogPlatform.entity.Post;
import com.example.BlogPlatform.repository.PostRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PostServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PostServiceImplTest {

  @MockBean
  private PostRepository postRepository;

  @Autowired
  private PostServiceImpl postServiceImpl;

  /**
   * Method under test: {@link PostServiceImpl#getAllByAccountId(Long)}
   */
  @Test
  void testGetAllByAccountId() {
    when(postRepository.findAllByAccountId((Long) any())).thenReturn(new ArrayList<>());
    assertTrue(postServiceImpl.getAllByAccountId(123L).isEmpty());
    verify(postRepository).findAllByAccountId((Long) any());
  }

  /**
   * Method under test: {@link PostServiceImpl#getAllByAccountId(Long)}
   */
  @Test
  void testGetAllByAccountId2() {
    Account account = new Account();
    account.setBlogName("Blog Name");
    account.setEmail("jane.doe@example.org");
    account.setId(123L);
    account.setName("Name");
    account.setPassword("iloveyou");
    account.setPosts(new ArrayList<>());

    Post post = new Post();
    post.setAccount(account);
    post.setBody("Not all who wander are lost");
    post.setCreatedAt(LocalDate.ofEpochDay(1L));
    post.setDeletedAt(LocalDate.ofEpochDay(1L));
    post.setId(123L);
    post.setImage("Image");
    post.setTitle("Dr");

    ArrayList<Post> postList = new ArrayList<>();
    postList.add(post);
    when(postRepository.findAllByAccountId((Long) any())).thenReturn(postList);
    assertTrue(postServiceImpl.getAllByAccountId(123L).isEmpty());
    verify(postRepository).findAllByAccountId((Long) any());
  }

  /**
   * Method under test: {@link PostServiceImpl#getAllByAccountId(Long)}
   */
  @Test
  void testGetAllByAccountId3() {
    Account account = new Account();
    account.setBlogName("Blog Name");
    account.setEmail("jane.doe@example.org");
    account.setId(123L);
    account.setName("Name");
    account.setPassword("iloveyou");
    account.setPosts(new ArrayList<>());

    Post post = new Post();
    post.setAccount(account);
    post.setBody("Not all who wander are lost");
    post.setCreatedAt(LocalDate.ofEpochDay(1L));
    post.setDeletedAt(LocalDate.ofEpochDay(1L));
    post.setId(123L);
    post.setImage("Image");
    post.setTitle("Dr");

    Account account1 = new Account();
    account1.setBlogName("Blog Name");
    account1.setEmail("jane.doe@example.org");
    account1.setId(123L);
    account1.setName("Name");
    account1.setPassword("iloveyou");
    account1.setPosts(new ArrayList<>());

    Post post1 = new Post();
    post1.setAccount(account1);
    post1.setBody("Not all who wander are lost");
    post1.setCreatedAt(LocalDate.ofEpochDay(1L));
    post1.setDeletedAt(LocalDate.ofEpochDay(1L));
    post1.setId(123L);
    post1.setImage("Image");
    post1.setTitle("Dr");

    ArrayList<Post> postList = new ArrayList<>();
    postList.add(post1);
    postList.add(post);
    when(postRepository.findAllByAccountId((Long) any())).thenReturn(postList);
    assertTrue(postServiceImpl.getAllByAccountId(123L).isEmpty());
    verify(postRepository).findAllByAccountId((Long) any());
  }

  /**
   * Method under test: {@link PostServiceImpl#getAllByAccountId(Long)}
   */
  @Test
  void testGetAllByAccountId4() {
    when(postRepository.findAllByAccountId((Long) any())).thenThrow(
        new IllegalStateException("foo"));
    assertThrows(IllegalStateException.class, () -> postServiceImpl.getAllByAccountId(123L));
    verify(postRepository).findAllByAccountId((Long) any());
  }

  /**
   * Method under test: {@link PostServiceImpl#getAllByAccountId(Long)}
   */
  @Test
  void testGetAllByAccountId5() {
    Account account = new Account();
    account.setBlogName("Blog Name");
    account.setEmail("jane.doe@example.org");
    account.setId(123L);
    account.setName("Name");
    account.setPassword("iloveyou");
    account.setPosts(new ArrayList<>());
    Post post = mock(Post.class);
    when(post.getDeletedAt()).thenReturn(null);
    doNothing().when(post).setAccount((Account) any());
    doNothing().when(post).setBody((String) any());
    doNothing().when(post).setCreatedAt((LocalDate) any());
    doNothing().when(post).setDeletedAt((LocalDate) any());
    doNothing().when(post).setId((Long) any());
    doNothing().when(post).setImage((String) any());
    doNothing().when(post).setTitle((String) any());
    post.setAccount(account);
    post.setBody("Not all who wander are lost");
    post.setCreatedAt(LocalDate.ofEpochDay(1L));
    post.setDeletedAt(LocalDate.ofEpochDay(1L));
    post.setId(123L);
    post.setImage("Image");
    post.setTitle("Dr");

    ArrayList<Post> postList = new ArrayList<>();
    postList.add(post);
    when(postRepository.findAllByAccountId((Long) any())).thenReturn(postList);
    assertEquals(1, postServiceImpl.getAllByAccountId(123L).size());
    verify(postRepository).findAllByAccountId((Long) any());
    verify(post).getDeletedAt();
    verify(post).setAccount((Account) any());
    verify(post).setBody((String) any());
    verify(post).setCreatedAt((LocalDate) any());
    verify(post).setDeletedAt((LocalDate) any());
    verify(post).setId((Long) any());
    verify(post).setImage((String) any());
    verify(post).setTitle((String) any());
  }
}

