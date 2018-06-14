package com.antonberezin.spacevalidationexamples.infra.util;

import java.lang.reflect.Field;

public class SpaceUtils {
    public static Object getField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getStringField(Object object, String fieldName) {
        return (String) getField(object, fieldName);
    }
}
