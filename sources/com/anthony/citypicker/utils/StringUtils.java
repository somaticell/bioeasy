package com.anthony.citypicker.utils;

public class StringUtils {
    public static String extractLocation(String city, String district) {
        return district.contains("县") ? district.substring(0, district.length() - 1) : city.substring(0, city.length() - 1);
    }
}
