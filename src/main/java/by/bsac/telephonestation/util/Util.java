package by.bsac.telephonestation.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final Pattern PATTERN_PHONE_NUMBER = Pattern.compile("[0-9]{4,7}");
    private static final Pattern PATTERN_SETUP_YEAR = Pattern.compile("[0-9]{1,4}");

    public static boolean checkString(String string) {
        try {
            int test = Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean validatePhoneNumber(String string) {
        Matcher mLogin = PATTERN_PHONE_NUMBER.matcher(string);
        return mLogin.matches();
    }

    public static boolean validateSetupYear(String string) {
        Matcher mLogin = PATTERN_SETUP_YEAR.matcher(string);
        return mLogin.matches();
    }

}
