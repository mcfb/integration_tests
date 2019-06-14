package edu.iis.mto.blog.domain.repository;


import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository repository;

    private User user;
    private BlogPost blogPost;
    private LikePost likePost;

    @Before
    public void setup(){
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Nowak");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);

        entityManager.persist(user);

        blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry("mypost");

        entityManager.persist(blogPost);

        likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);
    }

    @Test
    public void likedPostListShouldContainLikePost(){
        repository.save(likePost);
        List<LikePost> listLikedPosts = repository.findAll();
        assertTrue(listLikedPosts.contains(likePost));
    }

    @Test
    public void listLikedPostsShouldContainOnlyOnePostInside(){
        repository.save(likePost);
        List<LikePost> listLikedPosts = repository.findAll();
        int numOfPostExpected = 1;
        assertEquals(numOfPostExpected,listLikedPosts.size());
    }

    @Test
    public void likedPostShouldHaveProperUser(){
        repository.save(likePost);
        List<LikePost> listLikedPosts = repository.findAll();
        assertEquals(user,listLikedPosts.get(0).getUser());
    }

    @Test
    public void likedPostShouldHaveProperBlogPost(){
        repository.save(likePost);
        List<LikePost> listLikedPosts = repository.findAll();
        assertEquals(blogPost,listLikedPosts.get(0).getPost());
    }

    @Test
    public void shouldFindLikedPostForUser(){
        repository.save(likePost);
        Optional<LikePost> listLikedPosts = repository.findByUserAndPost(user,blogPost);
        assertEquals(likePost,listLikedPosts.get());
    }

}
