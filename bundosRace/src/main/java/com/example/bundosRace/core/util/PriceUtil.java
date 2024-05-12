package com.example.bundosRace.core.util;

public class PriceUtil {

    public static int convertDiscountPrice(int price, int discountRate) {
        return price - (price * discountRate / 100);
    }
}
