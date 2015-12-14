package com.vlasovartem.tvspace.utils.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by artemvlasov on 13/12/15.
 */
public class EntityValidatorTest {

    @Test
    public void validateTest () {
        Assert.assertTrue(EntityValidator.validate("4564", "\\d{4}"));
    }

    @Test
    public void validateWithInvalidDataTest () {
        Assert.assertFalse(EntityValidator.validate("hello", "\\d{4}"));
    }
}
