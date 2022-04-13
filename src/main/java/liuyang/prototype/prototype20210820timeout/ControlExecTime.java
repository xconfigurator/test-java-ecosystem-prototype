package liuyang.prototype.prototype20210820timeout;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 希望方法在指定时间内返回
 * 参考：https://blog.csdn.net/woshimyc/article/details/79369768
 *
 * @author liuyang
 * @date 2021/8/27
 */
@Slf4j
public class ControlExecTime {

    public static Long EXEC_TIME = 1000l;// 实际执行事件
    public static Long TIME_OUT = 999L;// 可以容忍的执行时间

    @ToString
    @Data
    static class IsAliveResponse {
        String resultIsAlive;
        String faultInfo;
    }

    @Data
    static class TimeControlReturnType<R> {
        boolean isTimeout;
        R response;
    }

    // TODO 考虑使用函数式编程接口优化
    public static <R> TimeControlReturnType<R> timeControlMethod(Class<R> returnType) {
        boolean timeoutFlag = false;
        TimeControlReturnType<R> tcr = new TimeControlReturnType<R>();
        tcr.setTimeout(timeoutFlag);

        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<R> call = new Callable<R>() {
            @Override
            public R call() throws Exception {
                // 模拟超时
                log.info("开始执行");
                IsAliveResponse isAliveResponse = new IsAliveResponse();
                isAliveResponse.setResultIsAlive("");
                TimeUnit.MILLISECONDS.sleep(EXEC_TIME);
                log.info("执行结束");
                return (R) isAliveResponse;
            }
        };

        try {
            Future<R> submitFuture = exec.submit(call);
            R r = submitFuture.get(TIME_OUT, TimeUnit.MILLISECONDS);
            timeoutFlag = false;
            //return timeoutFlag;
            tcr.setTimeout(timeoutFlag);
            tcr.setResponse(r);
            return tcr;
        } catch (Exception e) {
            log.error("执行超时");
            log.error(e.getMessage(), e);
            timeoutFlag = true;
            tcr.setTimeout(timeoutFlag);
            tcr.setResponse(null);
            //return timeoutFlag;
            return tcr;
        } finally {
            if (exec != null) {
                exec.shutdown();
            }
        }
    }

}
