package com.vlasovartem.tvspace.service.impl;

import com.vlasovartem.tvspace.entity.User;
import com.vlasovartem.tvspace.entity.enums.UserRole;
import com.vlasovartem.tvspace.persistence.repository.UserRepository;
import com.vlasovartem.tvspace.service.UserService;
import com.vlasovartem.tvspace.utils.exception.UserRegistrationException;
import com.vlasovartem.tvspace.utils.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Created by artemvlasov on 08/12/15.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signup(User user) throws UserRegistrationException {
        user.setRole(UserRole.USER);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        if(userRepository.countByUsernameIgnoreCase(user.getUsername()) > 0 ||
                userRepository.countByEmailIgnoreCase(user.getEmail()) > 0) {
            throw new UserRegistrationException("User with submitted email or username is exists");
        }
        userRepository.save(user);
    }
}
