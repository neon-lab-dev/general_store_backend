package com.neonlab.common.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MathUtilsTest {
    /**
     * Method under test:
     * {@link MathUtils#getDiscountedPrice(BigDecimal, BigDecimal)}
     */
    @Test
    void testGetDiscountedPrice() {
        // Arrange
        BigDecimal price = new BigDecimal("100");

        // Act
        BigDecimal actualDiscountedPrice = MathUtils.getDiscountedPrice(price, new BigDecimal("10"));

        // Assert
        assertEquals(new BigDecimal("90.00"), actualDiscountedPrice);
    }


    /**
     * Method under test:
     * {@link MathUtils#getDiscountedPrice(BigDecimal, BigDecimal)}
     */
    @Test
    void testGetDiscountedPrice4() {
        // Arrange
        BigDecimal price = new BigDecimal("1000");

        // Act
        BigDecimal actualDiscountedPrice = MathUtils.getDiscountedPrice(price, new BigDecimal("2.3"));

        // Assert
        assertEquals(new BigDecimal("977.00"), actualDiscountedPrice);
    }

    /**
     * Method under test: {@link MathUtils#getSum(List)}
     */
    @Test
    void testGetSum() {
        // Arrange and Act
        BigDecimal actualSum = MathUtils.getSum(new ArrayList<>());

        // Assert
        assertEquals(new BigDecimal("0.00"), actualSum);
    }

    /**
     * Method under test: {@link MathUtils#getSum(List)}
     */
    @Test
    void testGetSum2() {
        // Arrange
        ArrayList<BigDecimal> values = new ArrayList<>();
        values.add(new BigDecimal("2.3"));

        // Act
        BigDecimal actualSum = MathUtils.getSum(values);

        // Assert
        assertEquals(new BigDecimal("2.30"), actualSum);
    }

    /**
     * Method under test: {@link MathUtils#getSum(List)}
     */
    @Test
    void testGetSum3() {
        // Arrange
        ArrayList<BigDecimal> values = new ArrayList<>();
        values.add(BigDecimal.valueOf(100));
        values.add(BigDecimal.valueOf(100));
        values.add(BigDecimal.valueOf(100));

        // Act
        BigDecimal actualSum = MathUtils.getSum(values);

        // Assert
        assertEquals(new BigDecimal("300.00"), actualSum);
    }
}
