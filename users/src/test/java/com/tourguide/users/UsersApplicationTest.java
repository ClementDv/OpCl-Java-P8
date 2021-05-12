package com.tourguide.users;

import com.tourguide.users.config.ScheduledConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(ScheduledConfig.class)
class UsersApplicationTest {
    @Test
    void contextLoads() {
    }
}
