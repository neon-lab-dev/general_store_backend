package com.neonlab.common.utilities;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EncryptionUtilsTest {
    /**
     * Method under test:
     * {@link EncryptionUtils#getHmacSignature(String, String, String)}
     */
    @Test
    void testGetHmacSignature() throws InvalidKeyException, NoSuchAlgorithmException {
        var retVal = EncryptionUtils.getHmacSignature("123456", "baeldung", "HmacSHA256");
        Assertions.assertEquals("5b50d80c7dc7ae8bb1b1433cc0b99ecd2ac8397a555c6f75cb8a619ae35a0c35",
                retVal);
    }
}
