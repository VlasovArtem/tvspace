package com.vlasovartem.tvspace.service;

import com.vlasovartem.tvspace.entity.User;
import com.vlasovartem.tvspace.utils.exception.UserRegistrationException;

/**
 * Created by artemvlasov on 08/12/15.
 */
public interface UserService {
    void signup (User user) throws UserRegistrationException;
}
