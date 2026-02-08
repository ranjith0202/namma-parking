package com.park.users.util;

import java.util.Map;

public final class UserContext {

    private static final ThreadLocal<String> USER = new ThreadLocal<>();
    
    private static final ThreadLocal<Map<String, String>> CONTEXT = new ThreadLocal<>();


    private UserContext() {}

    public static void setUser(String user) {
        if (user != null && !user.isBlank()) {
            USER.set(user);
        }
    }

    public static String getUser() {
        return USER.get() != null ? USER.get() : "SYSTEM";
    }

    public static void clear() {
        USER.remove();
        CONTEXT.remove();
    }
    
    public static void setContext(Map<String, String> data) {
        CONTEXT.set(data);
    }

    // Convenience methods for specific fields
    

    public static String getUserName() {
        return get("userName");
    }

    public static String getRoles() {
        return get("roles");
    }

    public static String getCorrelationId() {
        return get("correlationId");
    }

    // Generic getter
    public static String get(String key) {
        Map<String, String> map = CONTEXT.get();
        return (map != null) ? map.get(key) : null;
    }

}

