package liuyang.prototype.prototype20211202queue.queue20211202.scheduler;

import liuyang.prototype.prototype20211202queue.queue20211202.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 监控这个LinkedBlockingQueue内容
 *
 * 目的是看一看是否真的是哪一个操作阻塞住了
 *
 * @author liuyang(wx)
 * @since 2022/4/3
 */
//@Service
@Slf4j
public class MonQueueJob {

    @Autowired
    QueueService queueService;

    //@Scheduled(cron = "0/5 * * * * *")
    @Scheduled(cron = "0/1 * * * * *")
    protected void mon() {
        log.info("queue size = {}", queueService.getQueue().size());
    }
}
