package com.renstation.common.util;
import com.renstation.common.constant.RegexConstants;
public class PhoneNumberUtils {
    public static boolean isValid(String phone) {
        return phone != null && phone.matches(RegexConstants.PHONE_NUMBER_PATTERN);
    }
}
