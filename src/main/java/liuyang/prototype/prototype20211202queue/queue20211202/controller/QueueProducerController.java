package liuyang.prototype.prototype20211202queue.queue20211202.controller;

import liuyang.prototype.common.utils.R;
import liuyang.prototype.prototype20211202queue.queue20211202.service.QueueProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyang(wx)
 * @since 2021/12/2
 */
@RestController
@RequestMapping("/queue")
@Slf4j
public class QueueProducerController {

    @Autowired
    QueueProducerService queueProducerService;

    @GetMapping("/hello")
    public R hello() {
        return R.ok("hello");
    }

    @PostMapping("/produce")
    public R produce(String msg) {
        Boolean b = false;// 貌似这个b一直是null，并没有意义！
        try {
            b = queueProducerService.doSomethingAsync(msg);// 这是一个异步方法
            return R.ok("成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error("失败");
        } finally {
            log.info("b = {}，异步方法所以返回都是null，这里只是测试异步方法返回值，实际异步方法可以声明称void， 注意线程池的编号，符合预期。", b);
        }
        //return b ? R.ok() : R.error();
    }

}
