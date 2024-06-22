package com.neonlab.common.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JsonUtilsTest.class})
@ExtendWith(SpringExtension.class)
class JsonUtilsTest {
    /**
     * Method under test: {@link JsonUtils#jsonOf(Object)}
     */
    @Test
    void testJsonOf() {
        // Arrange, Act and Assert
        assertEquals("", JsonUtils.jsonOf(null));
        assertEquals("42", JsonUtils.jsonOf(42));
        assertEquals("\"42\"", JsonUtils.jsonOf("42"));
        assertEquals("\"Value\"", JsonUtils.jsonOf("Value"));
    }
}
