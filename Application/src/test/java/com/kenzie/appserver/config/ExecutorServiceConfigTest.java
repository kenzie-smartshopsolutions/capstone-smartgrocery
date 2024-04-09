package com.kenzie.appserver.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ExecutorServiceConfig.class)
public class ExecutorServiceConfigTest {

    @Autowired
    private TaskExecutor taskExecutor;

    @Test
    void executorServiceBeanIsConfiguredCorrectly() {
        assertTrue(taskExecutor instanceof ThreadPoolTaskExecutor);
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) taskExecutor;
        assertEquals(4, executor.getCorePoolSize());
        assertEquals(4, executor.getMaxPoolSize());
    }
}

