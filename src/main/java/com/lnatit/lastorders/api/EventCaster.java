package com.lnatit.lastorders.api;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

import java.lang.reflect.Field;

public class EventCaster {
    public static <B, D extends B> D reinterpret_cast(B src, Class<D> derived, Class<B> base) {
        try {
            D ret = derived.cast(UnsafeAccess.UNSAFE.allocateInstance(derived));
            for (Field field : base.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(ret, field.get(src));
            }
            return ret;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
