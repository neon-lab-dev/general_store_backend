package com.neonlab.common.utilities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringUtilTest {
    /**
     * Method under test: {@link StringUtil#isNotBlank(String)}
     */
    @Test
    void testIsNotBlank() {
        // Arrange, Act and Assert
        assertTrue(StringUtil.isNotBlank("Str"));
        assertFalse(StringUtil.isNotBlank(null));
    }
}
