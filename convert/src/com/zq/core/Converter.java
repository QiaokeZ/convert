package com.zq.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Converter {

    public static <T> T convert(Object source, Class<T> target) {
        return convert(source, target, null);
    }

    public static <T> T convert(Object source, Class<T> target, Options options) {
        if (source == null || target == null) return null;
        options = (options == null ? Options.builder().build() : options);
        Set<String> whitelist = options.getWhitelist();
        Set<String> blacklist = options.getBlacklist();
        Map<String, String> mapping = options.getMapping();
        ObjectContext sourceContext = ObjectContext.get(source.getClass());
        try {
            T object = target.newInstance();
            ObjectContext targetContext = ObjectContext.get(object.getClass());
            for (Map.Entry<String, Field> entry : sourceContext.fields.entrySet()) {
                Field sourceField = entry.getValue();
                String name = entry.getKey();
                Object value = sourceField.get(source);
                if (value != null) {
                    if (mapping != null) {
                        String key = mapping.get(entry.getKey());
                        name = key != null ? key : entry.getKey();
                    }
                    if ((blacklist == null && whitelist == null)
                            || (whitelist != null && whitelist.contains(name))
                            || (blacklist != null && !blacklist.contains(name))) {
                        Field targetField = targetContext.fields.get(name);
                        if (targetField != null && sourceField.getType().isAssignableFrom(targetField.getType())) {
                            targetField.set(object, value);
                        }
                    }
                }
            }
            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class ObjectContext {

        static Map<Class<?>, ObjectContext> objectCache = new HashMap<>();
        Map<String, Field> fields;

        static Map<String, Field> findFields(Class<?> cls) {
            Map<String, Field> fields = new HashMap<>();
            while (cls != null) {
                for (Field field : cls.getDeclaredFields()) {
                    if (!Modifier.isFinal(field.getModifiers())) {
                        field.setAccessible(true);
                        fields.put(field.getName(), field);
                    }
                }
                cls = cls.getSuperclass();
            }
            return fields;
        }

        static synchronized ObjectContext get(Class<?> cls) {
            ObjectContext context = objectCache.get(cls);
            if (context == null) {
                context = new ObjectContext();
                context.fields = findFields(cls);
                objectCache.put(cls, context);
            }
            return context;
        }
    }
}
