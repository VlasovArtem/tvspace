package com.vlasovartem.tvspace.utils;

import com.vlasovartem.tvspace.entity.Actor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by artemvlasov on 22/11/15.
 */
@Component
public class ActorValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Actor.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println(target);
        System.out.println(errors);
    }
}
