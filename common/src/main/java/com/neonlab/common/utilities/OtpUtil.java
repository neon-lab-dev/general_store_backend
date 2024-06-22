package com.neonlab.common.utilities;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.Random;

@UtilityClass
public class OtpUtil {

    public static String generateOtp(int length) {
        Random random = new Random();
        int maxValue = (int) Math.pow(10, length) - 1;
        int randomNumber = random.nextInt(maxValue);
        String output = Integer.toString(randomNumber);

        while (output.length() < length) {
            output = "0" + output;
        }
        return output;
    }


}
