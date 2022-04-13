package liuyang.prototype.prototype20211202queue.queue20211202.service;

import liuyang.prototype.prototype20211202queue.queue20211202.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author liuyang(wx)
 * @since 2021/12/2
 */
@Service
@Slf4j
public class QueueConsumerService {
    // 注意！！！！！！！！ begin
    // 不可以写在这里！！应通过QueueConsumerInit来启动
    /*
    @PostConstruct
    public void doSomethingSequentially() {
        doService();
    }*/
    // 注意！！！！！！！！ end

    @Autowired
    QueueService queueService;

    //public static Long EXEC_TIME = 1000l;// 实际执行事件
    public static Long TIME_OUT = 6L;// 可以容忍的执行时间

    @Async// Spring 线程池管理的一个独立的线程执行它 (不能省略，否则会阻塞Spring Framework主线程，造成容器无法启动！)
    public void doService() {
        while (true) {// queue是一个阻塞队列，不用担心这样写会CPU 100%
            String msg = null;
            try {
                msg = queueService.getQueue().take();

                // 20220330 问题列表
                // TODO 措施：加超时控制 20220330
                // 措施：扩大异常处理范围 20220330 OK InterruptedException -> Exception
                // TODO 程序验证框架应该增加 1. 被调用方法随机抛出异常。 2. 被调用方法暂停执行的情况。
                // 措施：如何监控LinkedBlockingQueue内容 -> MonAlmQueueJob
                //doSomething(msg);

                // 20220403 处理方案
                doSomethingWithTimeoutControl(msg);

             } catch (TimeoutException e) {
                log.error("处理消息 msg = {} 超时", msg);
                log.error(e.getMessage(), e);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void doSomethingWithTimeoutControl(String msg) throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            doSomething(msg);
            return 0;
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        futureTask.get(TIME_OUT, TimeUnit.SECONDS);
    }

    private void doSomething(String msg) throws Exception {
        // 这里执行真正的业务了逻辑。比如入库
        log.info("排队的消息数{}, 正在处理消息{}" , queueService.getQueue().size(), msg);

        // 模拟数据库缓慢执行
        TimeUnit.SECONDS.sleep(4);

        // 异常场景1：模拟超时
        // 执行动作超时造成队列阻塞，消息只进不出。
        // 处理：在外部增加超时控制，超出阈值则终止任务（TODO 是否会对数据库操作造成不良影响待观察。 202204031430）
        // 预期：程序框架应能够在阈值限定范围内持续消费，不至于卡死。
        if (queueService.getQueue().size() == 9) {
            log.info("########################模拟超时");
            TimeUnit.SECONDS.sleep(60);
        }

        // 异常场景2：模拟运行时异常
        // 预期：程序框架应该能消化异常并继续执行
        if (queueService.getQueue().size() == 5) {
            log.info("########################模拟运行时异常");
            throw new RuntimeException("我就是故意的");
        }
    }
}
