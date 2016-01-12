package com.vlasovartem.tvspace.service;

import com.vlasovartem.tvspace.entity.User;
import com.vlasovartem.tvspace.persistence.repository.UserRepository;
import com.vlasovartem.tvspace.service.impl.UserServiceImpl;
import com.vlasovartem.tvspace.utils.exception.UserRegistrationException;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.*;

/**
 * Created by artemvlasov on 14/12/15.
 */
@RunWith(EasyMockRunner.class)
public class UserServiceTest {

    private User user;

    @TestSubject
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;

    @Before
    public void setup () {
        user = new User();
        user.setEmail("validemail@mail.com");
        user.setUsername("validusername");
        user.setName("Valid Name");
        user.setPassword("validpassword");
    }

    @Test
    public void signupTest() throws UserRegistrationException {
        user.setId("test");
        expect(userRepository.save(user)).andReturn(user);
        expect(userRepository.countByEmailIgnoreCase(user.getEmail())).andReturn(0);
        expect(userRepository.countByUsernameIgnoreCase(user.getUsername())).andReturn(0);
        replay(userRepository);
        userService.signup(user);
    }

    @Test(expected = UserRegistrationException.class)
    public void signupWithExistsEmailTest () throws UserRegistrationException {
        expect(userRepository.countByUsernameIgnoreCase(user.getUsername())).andReturn(0);
        expect(userRepository.countByEmailIgnoreCase(user.getEmail())).andReturn(1);
        replay(userRepository);
        userService.signup(user);
    }

    @Test(expected = UserRegistrationException.class)
    public void signupWithExistsUsernameTest () throws UserRegistrationException {
        expect(userRepository.countByUsernameIgnoreCase(user.getUsername())).andReturn(1);
        expect(userRepository.countByEmailIgnoreCase(user.getEmail())).andReturn(0);
        replay(userRepository);
        userService.signup(user);
    }

}
