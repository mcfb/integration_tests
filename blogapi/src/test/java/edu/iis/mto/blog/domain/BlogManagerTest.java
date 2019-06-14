package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.BlogDataMapper;
import edu.iis.mto.blog.services.BlogService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    private LikePostRepository postRepository;

    @MockBean
    private BlogPostRepository blogRepository;

    @Autowired
    BlogDataMapper dataMapper;

    @Autowired
    BlogService blogService;

    private User postUser;
    private User likeUser;

    @Before
    public void setUp(){
        postUser = new User();
        postUser.setFirstName("Jan");
        postUser.setLastName("Nowak");
        postUser.setEmail("jan.nowak@dmcs.pl");
        postUser.setId(0L);

        likeUser = new User();
        likeUser.setFirstName("Ala");
        likeUser.setLastName("Kowalska");
        likeUser.setEmail("ala.kow@dmcs.pl");
        likeUser.setId(1L);
    }


    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        ArgumentCaptor<User> userParam = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        Assert.assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }


    @Test
    public void userWithConfirmedAccoundCanLikePost() {


        postUser.setAccountStatus(AccountStatus.NEW);
        userRepository.save(postUser);
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(postUser));


        likeUser.setAccountStatus(AccountStatus.CONFIRMED);
        userRepository.save(likeUser);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(likeUser));

        BlogPost blogPost = new BlogPost();
        blogPost.setUser(postUser);
        blogPost.setEntry("mypost");
        blogPost.setId(0L);
        Mockito.when(blogRepository.findById(0L)).thenReturn(Optional.of(blogPost));

        Mockito.when(postRepository.findByUserAndPost(likeUser, blogPost)).thenReturn(Optional.empty());
        blogService.addLikeToPost(likeUser.getId(), blogPost.getId());

        ArgumentCaptor<LikePost> captor = ArgumentCaptor.forClass(LikePost.class);
        Mockito.verify(postRepository).save(captor.capture());

        LikePost likePost = captor.getValue();
        Assert.assertEquals(likePost.getPost(), blogPost);
        Assert.assertEquals(likePost.getUser(), likeUser);

    }

    @Test(expected = DomainError.class)
    public void userWithoutConfirmedAccoundCannotLikePost() {

        postUser.setAccountStatus(AccountStatus.NEW);
        userRepository.save(postUser);
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(postUser));

        likeUser.setAccountStatus(AccountStatus.NEW);
        userRepository.save(likeUser);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(likeUser));

        BlogPost blogPost = new BlogPost();
        blogPost.setUser(postUser);
        blogPost.setEntry("mypost");
        blogPost.setId(0L);
        Mockito.when(blogRepository.findById(0L)).thenReturn(Optional.of(blogPost));

        Mockito.when(postRepository.findByUserAndPost(likeUser, blogPost)).thenReturn(Optional.empty());
        blogService.addLikeToPost(likeUser.getId(), blogPost.getId());

        ArgumentCaptor<LikePost> captor = ArgumentCaptor.forClass(LikePost.class);
        Mockito.verify(postRepository).save(captor.capture());

        LikePost likePost = captor.getValue();
        Assert.assertEquals(likePost.getPost(), blogPost);
        Assert.assertEquals(likePost.getUser(), likeUser);

    }


}
