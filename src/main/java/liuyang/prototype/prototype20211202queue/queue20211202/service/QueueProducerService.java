package liuyang.prototype.prototype20211202queue.queue20211202.service;

import liuyang.prototype.prototype20211202queue.queue20211202.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author liuyang(wx)
 * @since 2021/12/2
 */
@Service
@Slf4j
public class QueueProducerService {

    @Autowired
    QueueService queueService;

    @Async// 需要异步执行，加速响应。 本方法把msg放入queue中就立即返回。
    public Boolean doSomethingAsync(String msg) {
        try {
            queueService.getQueue().put(msg);
            return true;// 这个貌似返回不了
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            return false;// 这个貌似返回不了
        }
    }
}
