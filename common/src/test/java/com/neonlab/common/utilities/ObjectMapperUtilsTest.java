package com.neonlab.common.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.neonlab.common.expectations.ServerException;
import org.junit.jupiter.api.Test;

class ObjectMapperUtilsTest {
    /**
     * Method under test: {@link ObjectMapperUtils#map(Object, Class)}
     */
    @Test
    void testMap() throws ServerException {
        // Arrange
        Class<Object> destinationClass = Object.class;

        // Act and Assert
        assertEquals("Source", ObjectMapperUtils.map("Source", destinationClass));
    }

    /**
     * Method under test: {@link ObjectMapperUtils#map(Object, Class)}
     */
    @Test
    void testMap2() throws ServerException {
        // Arrange, Act and Assert
        assertThrows(ServerException.class, () -> ObjectMapperUtils.map(null, null));
    }

    /**
     * Method under test: {@link ObjectMapperUtils#map(Object, Class)}
     */
    @Test
    void testMap3() throws ServerException {
        // Arrange, Act and Assert
        assertThrows(ServerException.class, () -> ObjectMapperUtils.map("42", null));
    }

    /**
     * Method under test: {@link ObjectMapperUtils#map(Object, Class)}
     */
    @Test
    void testMap4() throws ServerException {
        // Arrange
        Class<Object> destinationClass = Object.class;

        // Act and Assert
        assertEquals("42", ObjectMapperUtils.map("42", destinationClass));
    }

    /**
     * Method under test: {@link ObjectMapperUtils#map(Object, Class)}
     */
    @Test
    void testMap5() throws ServerException {
        // Arrange
        Class<ObjectMapperUtils> destinationClass = ObjectMapperUtils.class;

        // Act and Assert
        assertThrows(ServerException.class, () -> ObjectMapperUtils.map("Source", destinationClass));
    }

    /**
     * Method under test: {@link ObjectMapperUtils#map(Object, Class)}
     */
    @Test
    void testMap7() throws ServerException {
        // Arrange
        Class<ObjectMapperUtils> destinationClass = ObjectMapperUtils.class;

        // Act and Assert
        assertThrows(ServerException.class,
                () -> ObjectMapperUtils.map("This is a utility class and cannot be instantiated", destinationClass));
    }
}
