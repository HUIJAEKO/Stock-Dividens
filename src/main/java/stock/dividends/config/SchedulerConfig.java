package stock.dividends.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();

        int n = Runtime.getRuntime().availableProcessors();
        threadPool.setCorePoolSize(n);
        threadPool.initialize();

        taskRegistrar.setTaskScheduler((TaskScheduler) threadPool);
    }
}
