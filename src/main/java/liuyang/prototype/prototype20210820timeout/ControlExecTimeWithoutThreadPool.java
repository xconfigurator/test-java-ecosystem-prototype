package liuyang.prototype.prototype20210820timeout;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author liuyang
 * @date 2021/9/4
 */
@Slf4j
public class ControlExecTimeWithoutThreadPool {

    public static Long EXEC_TIME = 1000l;// 实际执行事件
    public static Long TIME_OUT = 1999L;// 可以容忍的执行时间

    public static void main(String[] args) {
        // FutureTask https://docs.oracle.com/javase/9/docs/api/java/util/concurrent/FutureTask.html
        // 装一个Callable类型的 https://docs.oracle.com/javase/9/docs/api/java/util/concurrent/Callable.html
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(EXEC_TIME);
            return 1000;
        });

        // 运行
        Thread thread = new Thread(futureTask);
        thread.start();

        // 阻塞，等着task运行完。
        // FutureTask.get() : Waits if necessary for the computation to complete, and then retrieves its result.
        try {
            log.info("futureTask return  = {}", futureTask.get(TIME_OUT, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        } catch (TimeoutException e) {
            log.error(e.getMessage(), e);
        } finally {
            // 实测，无论是否发生超时异常都是: false。
            log.info("futureTask thread.isAlive() = {}", thread.isAlive());
            // 实测，无论是否发超时生异常都是：futureTask = java.util.concurrent.FutureTask@307f6b8c[Completed normally]
            log.info("futureTask = {}", futureTask);
            // 实测，无论是否发超时生异常都是：true
            log.info("futureTask.isDone() = {}", futureTask.isDone());
            // 实测，无论是否发超时生异常都是：false
            log.info("futureTask.isCancelled() = {}", futureTask.isCancelled());
        }

    }
}
