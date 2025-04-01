package com.waystech.authmanagement.Utils;

import java.util.Random;

public class OtpGenerator {

    public static String generateOtpString() {

        int min = 10000;
        int max = 99999;

        Random random = new Random();
        int randomNum = random.nextInt(max - min + 1) + min;

        return String.valueOf(randomNum);

    }

}
