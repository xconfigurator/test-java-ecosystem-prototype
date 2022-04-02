package liuyang.prototype.prototype20211202queue.queue20211202.service;

import liuyang.prototype.prototype20211202queue.queue20211202.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author liuyang(wx)
 * @since 2021/12/2
 */
@Service
@Slf4j
public class QueueConsumerService {
    @Autowired
    QueueService queueService;

    // 不可以写在这里！！应通过QueueConsumerInit来启动
    /*
    @PostConstruct
    public void doSomethingSequentially() {
        doService();
    }*/

    @Async// Spring 线程池管理的一个独立的线程执行它 (不能省略，否则会阻塞Spring Framework主线程，造成容器无法启动！)
    public void doService() {
        while (true) {// queue是一个阻塞队列，不用担心这样写会CPU 100%
            try {
                String msg = queueService.getQueue().take();

                // 这里执行真正的业务了逻辑。比如入库
                log.info("排队的消息数{}, 正在处理消息{}" , queueService.getQueue().size(), msg);

                // 模拟数据库缓慢执行
                TimeUnit.SECONDS.sleep(4);

            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
