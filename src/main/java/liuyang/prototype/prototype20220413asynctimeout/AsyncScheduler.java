package liuyang.prototype.prototype20220413asynctimeout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author liuyang(wx)
 * @since 2022/4/13
 */
@Service
@Slf4j
public class AsyncScheduler {

    @Autowired
    private AsyncService asyncService;

    public void runAsyncTasks() {
        for (int i = 0; i < 5; i++) {

        }
    }

    //@Scheduled(cron = "0/1 * * * * *")
    public void runAsyncTasks1() throws InterruptedException, ExecutionException, TimeoutException {
        log.info("runAsyncTask1");
        asyncService.doSomething();
    }


    //@Scheduled(cron = "0/1 * * * * *")
    public void runAsyncTasks2() throws InterruptedException, ExecutionException, TimeoutException {
        log.info("runAsyncTask2");
        Future<String> stringFuture = asyncService.doSomething();
        stringFuture.get(5, TimeUnit.SECONDS);
    }

    //@Scheduled(cron = "0/1 * * * * *")
    public void runAsyncTask3() {
        log.info("runAsyncTask3");
        try {
            asyncService.runWithTimeControl();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        ;
    }

    @Scheduled(cron = "0/6 * * * * *")
    public void runAsyncTask4() {
        log.info("runAsyncTask4");
        try {
            asyncService.doSomethingWithTimeControl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
