package com.supershaun.bikeshop.utils;

import java.util.*;
import java.util.stream.Collectors;

public class SpecificationParser {

    // key:value;key:value
    public static Map<String, String> parseEntries(String specifications) {
        Map<String, String> specificationsMap = new HashMap<>();
        String[] items = specifications.split(";");

        if (items.length == 0)
            return specificationsMap;

        String[] values;

        for (String item : items) {
            values = item.split(":");
            if (values.length == 2)
                specificationsMap.put(values[0], values[1]);
        }

        return specificationsMap;
    }

    // key;key
    public static List<String> parseKeysAndOrder(String specifications) {
        List<String> keys = parseKeys(specifications);
        return keys.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<String> parseKeys(String specifications) {
        List<String> keys = Arrays.asList(specifications.split(";"));
        return keys;
    }

    public static String parseValueByKey(String specifications, String key) {
        Map<String, String> entries = parseEntries(specifications);
        return entries.get(key);
    }
}
