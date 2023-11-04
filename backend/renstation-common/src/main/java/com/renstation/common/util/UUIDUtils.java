package com.renstation.common.util;
import java.util.UUID;
public class UUIDUtils {
    public static boolean isValid(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
