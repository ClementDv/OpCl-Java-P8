package com.tourguide.users.config;

import io.micrometer.core.lang.NonNull;
import io.micrometer.core.lang.NonNullApi;
import io.micrometer.core.lang.Nullable;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@TestConfiguration
public class ScheduledConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        // Returns a disabled TaskScheduler
        return new TaskScheduler() {
            @Override
            public ScheduledFuture<?> schedule(@NonNull Runnable task, @NonNull Trigger trigger) {
                return null;
            }

            @Override
            public ScheduledFuture<?> schedule(@NonNull Runnable task, @NonNull Date startTime) {
                return null;
            }

            @Override
            public ScheduledFuture<?> scheduleAtFixedRate(@NonNull Runnable task, @NonNull Date startTime, long period) {
                return null;
            }

            @Override
            public ScheduledFuture<?> scheduleAtFixedRate(@NonNull Runnable task, long period) {
                return null;
            }

            @Override
            public ScheduledFuture<?> scheduleWithFixedDelay(@NonNull Runnable task, @NonNull Date startTime, long delay) {
                return null;
            }

            @Override
            public ScheduledFuture<?> scheduleWithFixedDelay(@NonNull Runnable task, long delay) {
                return null;
            }
        };
    }

}
