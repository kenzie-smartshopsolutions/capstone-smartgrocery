package com.kenzie.appserver.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UuidUtils {

    public static boolean isValidUUID(String uuid) {
        if (uuid == null) {
            return false;
        }
        try {
            java.util.UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /** UUID validator testing **/
//    public static void main(String[] args) {
//        // Test cases
//        System.out.println(isValidUUID("d290f1ee-6c54-4b01-90e6-d701748f0851")); // true
//        System.out.println(isValidUUID("not-a-uuid")); // false
//        System.out.println(isValidUUID(null)); // false
//    }
}
