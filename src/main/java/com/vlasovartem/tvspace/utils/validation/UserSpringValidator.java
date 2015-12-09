package com.vlasovartem.tvspace.utils.validation;

import com.vlasovartem.tvspace.entity.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

import static com.vlasovartem.tvspace.utils.validation.UserValidator.PersonValidationInfo.*;

/**
 * Created by artemvlasov on 08/12/15.
 */
@Component
public class UserSpringValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required", "Please provide a email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required", "Please provide an username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required", "Please provide an password");
        if(Objects.nonNull(user.getName()) && !UserValidator.validate(user.getName(), NAME.getPattern())) {
            errors.rejectValue("name", NAME.getError());
        }
        if(!UserValidator.validate(user.getUsername(), USERNAME.getPattern())) {
            errors.rejectValue("username", USERNAME.getError());
        }
        if(!EmailValidator.getInstance().isValid(user.getEmail())) {
            errors.rejectValue("email", EMAIL.getError());
        }
        if(!UserValidator.validate(user.getPassword(), PASSWORD.getPattern())) {
            errors.rejectValue("password", PASSWORD.getError());
        }
    }
}
