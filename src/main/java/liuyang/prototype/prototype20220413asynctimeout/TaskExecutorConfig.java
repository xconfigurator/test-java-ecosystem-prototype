package liuyang.prototype.prototype20220413asynctimeout;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 消息队列任务的TaskExecutor
 *
 * @author suhj
 * @version 1.0 20180920
 */
//@Configuration
@EnableAsync // liuyang 这个注释是为了使用Spring默认的线程池以供@Async使用（在实时任务的*Listener中使用）
public class TaskExecutorConfig {
    /**
     * 用于处理来自于消息队列任务的TaskExecutor
     *
     * @param corePoolSize 1
     * @param maxPoolSize 2
     * @param keepAliveTime 3
     * @param queueCapacity 4
     * @return Task
     */
    //@Bean(name = "consumingTaskExecutor")
    //@Bean
    public ThreadPoolTaskExecutor getConsumingTaskExecutor(@Value("${mb.consumingexecutor.core-pool-size}") int corePoolSize,
                                                 @Value("${mb.consumingexecutor.max-pool-size}") int maxPoolSize,
                                                 @Value("${mb.consumingexecutor.keep-alive-time}") int keepAliveTime,
                                                 @Value("${mb.consumingexecutor.queue-capacity}") int queueCapacity) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setQueueCapacity(queueCapacity);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return executor;
    }

}
