package br.com.agoliveira.springbootcommysql.repository;

import br.com.agoliveira.springbootcommysql.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    @MockBean
    private UserRepository userRepository;

    private User firstUser = new User("First User", "username", "first.user@firstuser.com", "1passw04d!");

    @Before
    public void setUp() throws Exception {
        this.userRepository.save(firstUser);
    }

    @After
    public final void tearDown() {
        this.userRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void testFindFirstUser() {

        BDDMockito.given(this.userRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new User()));
        BDDMockito.given(this.userRepository.save(Mockito.any(User.class))).willReturn(firstUser);
    }
}
