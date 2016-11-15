package com.fifaminer.client.util;

import static java.util.Objects.isNull;

public class Strings {

    public static boolean isNullOrEmpty(String string) {
        return isNull(string) || string.isEmpty();
    }
}
