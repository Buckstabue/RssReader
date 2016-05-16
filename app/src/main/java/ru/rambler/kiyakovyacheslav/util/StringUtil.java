package ru.rambler.kiyakovyacheslav.util;

import android.support.annotation.Nullable;

public class StringUtil {
    private StringUtil() {
        // not intended to instantiate
    }

    @Nullable
    public static String trim(String s) {
        return s == null ? s : s.trim();
    }
}
