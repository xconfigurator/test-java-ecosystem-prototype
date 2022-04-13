package liuyang.prototype.prototype20211202queue.queue20211202.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liuyang(wx)
 * @since 2021/12/2
 */
@Configuration
@EnableAsync// 需要开启异步方法
@EnableScheduling// 监控需要
public class QueueConfig {
}
