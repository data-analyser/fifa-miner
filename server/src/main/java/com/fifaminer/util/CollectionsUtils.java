package com.fifaminer.util;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class CollectionsUtils {

    public static <T> Set<T> transformToSet(List<T> values) {
        return values.stream()
                .collect(toSet());
    }

    public static <T> List<T> transformToList(Set<T> values) {
        return values.stream()
                .collect(toList());
    }
}
