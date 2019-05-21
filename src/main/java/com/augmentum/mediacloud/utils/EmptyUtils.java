package com.augmentum.mediacloud.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class EmptyUtils {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String && (obj.toString().length() == 0 || obj.toString().trim().isEmpty())) {
            return true;
        }

        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }

        if (obj instanceof Collection && ((Collection<?>) obj).isEmpty()) {
            return true;
        }

        if (obj instanceof Map && ((Map<?, ?>) obj).isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
