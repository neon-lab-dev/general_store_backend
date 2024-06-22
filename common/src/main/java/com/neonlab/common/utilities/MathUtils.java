package com.neonlab.common.utilities;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@UtilityClass
public class MathUtils {

    public static final int DEFAULT_SCALE = 2;

    public static BigDecimal getDiscountedPrice(BigDecimal price, BigDecimal discountPercentage){
        return price.subtract((discountPercentage.divide(BigDecimal.valueOf(100))).multiply(price)).setScale(DEFAULT_SCALE);
    }

    public static BigDecimal getDiscountPercent(BigDecimal price,BigDecimal discountedPrice){
        BigDecimal discount = price.subtract(discountedPrice);
        return discount.divide(price, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }


    public static BigDecimal getSum(List<BigDecimal> values){
        var retVal = BigDecimal.valueOf(0);
        for (var value: values){
            retVal = retVal.add(value);
        }
        return retVal.setScale(DEFAULT_SCALE);
    }

}
