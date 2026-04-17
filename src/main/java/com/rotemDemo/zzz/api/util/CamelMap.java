package com.rotemDemo.zzz.api.util;

import java.io.Serial;
import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

public class CamelMap extends HashMap<String, Object> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public Object put(String key, Object value) {
        String finalKey = key;
        if (key != null && key.contains("_")) {
            finalKey = toCamelCase(key);
        }
        return super.put(finalKey, value);
    }

    private String toCamelCase(String str) {
        return JdbcUtils.convertUnderscoreNameToPropertyName(str);
    }
}
