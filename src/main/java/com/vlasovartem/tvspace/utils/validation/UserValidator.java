package com.vlasovartem.tvspace.utils.validation;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vlasovartem.tvspace.entity.User;
import com.vlasovartem.tvspace.utils.exception.EntityValidationException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * {@code PersonValidator} validate name, login, password, email, phone number, description of
 * {@code Person}.
 * Return true if validate data is matches patterns. Throws {@code EntityValidationException} with error message if
 * validate data not matches pattern.
 */
public class UserValidator extends EntityValidator {
    /**
     * Validate {@code PersonValidator}
     * @param {@link User} Optional of {@code PersonValidator}
     * @return true if all validate data matches their patterns otherwise throw {@code EntityValidationException}
     */
    public static boolean validate(User user, boolean update) {
        if(isNull(user)) {
            return false;
        } else {
            ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
            Arrays.stream(PersonValidationInfo.values()).allMatch(dc -> {
                if(update && dc.equals(PersonValidationInfo.PASSWORD)) {
                    return true;
                }
                if (!validate(dc, user)) {
                    objectNode.put(dc.name().toLowerCase(), dc.getError());
                }
                return true;
            });
            if (objectNode.size() != 0) {
                throw new EntityValidationException(objectNode, "Person form contains invalid data");
            }
            return true;
        }
    }

    /**
     * Validate {@code PersonValidator} data
     * @param info Enum for one of validated data
     * @param user Validated object
     * @return true if validated data is matches pattern
     */
    private static boolean validate(PersonValidationInfo info, User user) {
        switch (info) {
            case NAME:
                if(Objects.equals(user.getName(), "")) {
                    user.setName(null);
                }
                return Objects.isNull(user.getName()) ||
                        validate(user.getName(), info.pattern);
            case USERNAME:
                return validate(user.getUsername(), info.pattern);
            case PASSWORD:
                return validate(user.getPassword(), info.pattern);
            case EMAIL:
                return EmailValidator.getInstance().isValid(user.getEmail());
            default:
                return false;
        }
    }

    /**
     * Enum contains error messages and patterns for validating data
     */
    public enum PersonValidationInfo {
        NAME("Last name should not contains any digits and length should be 6 - 50",
                "^[\\p{L} .'\\-]{6,50}$"),
        USERNAME("Login should contains next characters: a-z 0-9 _ -. And length should be 6 - 100",
                "^[A-Za-z0-9_\\- .]{6,100}$"),
        PASSWORD("Min length of password should be 8 and max 128",
                "^.{8,128}$"),
        EMAIL("Email is not matches standard pattern",
                "");
        private String error;
        private String pattern;

        PersonValidationInfo(String error, String pattern) {
            this.error = error;
            this.pattern = pattern;
        }

        public String getError() {
            return error;
        }

        public String getPattern() {
            return pattern;
        }
    }
}
