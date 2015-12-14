package com.vlasovartem.tvspace.utils.validation;

import com.vlasovartem.tvspace.entity.User;
import com.vlasovartem.tvspace.utils.exception.EntityValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.vlasovartem.tvspace.utils.validation.UserValidator.*;
import static org.junit.Assert.*;

/**
 * Created by artemvlasov on 13/12/15.
 */
public class UserValidatorTest {
    private User user;

    @Before
    public void setup () {
        user = new User();
        user.setEmail("validemail@mail.com");
        user.setUsername("validusername");
        user.setName("Valid Name");
        user.setPassword("validpassword");
    }

    @Test
    public void validateTest () {
        assertTrue(validate(user, false));
    }

    @Test
    public void validateWithNullTest () {
        assertFalse(validate(null, false));
    }

    @Test(expected = EntityValidationException.class)
    public void validateWithInvalidNameTest () {
        user.setName("123");
        validate(user, false);
    }

    @Test
    public void validateWithEmptyNameTest () {
        user.setName("");
        validate(user, false);
    }

    @Test
    public void validateWithNullNameTest () {
        user.setName(null);
        assertTrue(validate(user, false));
    }

    @Test(expected = EntityValidationException.class)
    public void validateWithInvalidUsernameTest () {
        user.setUsername("1n");
        validate(user, false);
    }

    @Test(expected = EntityValidationException.class)
    public void validateWithInvalidEmailTest () {
        user.setEmail("test");
        validate(user, false);
    }

    @Test(expected = EntityValidationException.class)
    public void validateWithInvalidPasswordTest () {
        user.setPassword("11");
        validate(user, false);
    }

    @Test
    public void validateWithUpdateTest () {
        user.setPassword(null);
        validate(user, true);
    }

}
