package com.neonlab.common.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;

class DateUtilsTest {

    /**
     * Method under test: {@link DateUtils#getUnixTime(Date)}
     */
    @Test
    void testGetUnixTime() {
        // Arrange, Act and Assert
        assertEquals(0L,
                DateUtils.getUnixTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
    }

    /**
     * Method under test: {@link DateUtils#getUnixTime(java.util.Date)}
     */
    @Test
    void testGetUnixTime2() {
        // Arrange
        java.sql.Date date = mock(java.sql.Date.class);
        when(date.getTime()).thenReturn(10L);

        // Act
        long actualUnixTime = DateUtils.getUnixTime(date);

        // Assert
        verify(date).getTime();
        assertEquals(0L, actualUnixTime);
    }

}
