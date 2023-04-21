package cn.com.bioeasy.app.validator;

import java.util.regex.Pattern;

public class Validator {
    public static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    public static final String NUMBER_REGEX = "^[1-9]+(([0-9]*\\.{1}[0-9])|[0-9]*)+[0-9]*$|^0{1}\\.[0-9]*$";

    public static boolean isNumeric(String str) {
        if (!Pattern.compile(NUMBER_REGEX).matcher(str).matches()) {
            return false;
        }
        return true;
    }

    public static boolean isEmail(String str) {
        return Pattern.compile(EMAIL_REGEX).matcher(str).find();
    }
}
