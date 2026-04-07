package com.smartrice.admin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AdminSeedPasswordTest {

    private static final Pattern ADMIN_INSERT_PATTERN =
            Pattern.compile("\\(1,\\s*'admin123',\\s*'([^']+)'");

    @Test
    void seedAdminPasswordMatchesReadmeCredential() throws IOException {
        String sql = Files.readString(Path.of("../smartrice-db/sql/smartrice_seed.sql"));
        Matcher matcher = ADMIN_INSERT_PATTERN.matcher(sql);
        assertTrue(matcher.find(), "smartrice_seed.sql must contain the admin123 seed row");

        String hash = matcher.group(1);
        assertTrue(new BCryptPasswordEncoder().matches("admin123", hash),
                "README credential admin123/admin123 must match the seeded admin password hash");
    }
}
