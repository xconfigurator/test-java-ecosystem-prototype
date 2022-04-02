package liuyang.prototype.prototype20211202queue.queue20211202;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author liuyang(wx)
 * @since 2021/12/2
 */
@Component
@Slf4j
@Data
public class QueueService {
    // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/BlockingQueue.html
    // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/LinkedBlockingQueue.html
    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
}
