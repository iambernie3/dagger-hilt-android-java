package com.gte.myjavadaggerhilt.core;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionsFilter {
    public<T> List<T> filter(Predicate<T> criteria, List<T> list) {
        return list.stream().filter(criteria).collect(Collectors.<T>toList());
    }
}
