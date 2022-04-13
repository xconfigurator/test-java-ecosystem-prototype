package liuyang.prototype.prototype20220413asynctimeout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author liuyang(wx)
 * @since 2022/4/13
 */
@Service
@Slf4j
public class AsyncService {

    private static final int TIME_OUT = 5000; // 毫秒 (略小于调度周期即可)
    private static final int RANDOM_SLEEP_TIME_UPPER_BOUND = 10000; // 毫秒  预计有一半的任务超时
    private static Random random = new Random();

    // 202204131632 这样不行！
    @Async
    public void runWithTimeControl() throws InterruptedException, ExecutionException, TimeoutException {
        log.info("runWithTimeControl");
        Future<String> stringFuture = doSomething();
        stringFuture.get(5, TimeUnit.SECONDS);
    }

    // 202204131632 这样不行！
    @Async
    public Future<String> doSomething() {
        log.info("doSomething begin");
        // 模拟执行时间
        try {
            // 异步任务执行60s，如果不加超时控制，则预期“当前线程池中的线程数/活跃线程数”中的数量会持续增长，待增加超时控制之后预期“当前线程池中的线程数/活跃线程数”会回落。
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("doSomething end");
        return new AsyncResult<>("test");
    }

    // 202204131718 这样做可以！
    @Async
    public void doSomethingWithTimeControl() {
        // 在这个里面设置超时
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            return doSomethingSync();
        });
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            String result = futureTask.get(TIME_OUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private String doSomethingSync() {
        try {
            //TimeUnit.SECONDS.sleep(60);
            TimeUnit.MILLISECONDS.sleep(getRandomSleepTime());
            return "foo";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "bar";
        }
    }

    private static int getRandomSleepTime() {
        return random.nextInt(RANDOM_SLEEP_TIME_UPPER_BOUND);
    }
}
