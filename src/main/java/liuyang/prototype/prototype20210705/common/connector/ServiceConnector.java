package liuyang.prototype.prototype20210705.common.connector;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 只需要重写adaptVendor方法
 * 上层调用的时候，使用getPorts方法
 *
 * @param <T>
 */
@Slf4j
public abstract class ServiceConnector<T> {

    public abstract Optional<T> adaptVendor(ConnInfo connInfo);

    // 可以模板化的部分
    public List<T> getPorts(List<ConnInfo> connInfos) {
        List<T> result = new ArrayList<>();
        if (connInfos != null) {
            for (ConnInfo connInfo : connInfos) {
                Optional<T> port = adaptVendor(connInfo);
                if (port.isPresent()) {
                    result.add(port.get());
                }
            }
        }
        return result;
    }

    // 可模板化的部分
    protected Optional<T> getPort(String wsdlLocation, String adapterClassName){
        log.debug("wsdlLocation = " + wsdlLocation);
        T port = null;
        try {
            port = (T) Class.forName(adapterClassName).getConstructor(String.class).newInstance(wsdlLocation);
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            // 策略1：仍然可以启动
            // 这样，仅仅是这个厂商的连接会出问题，但整个应用会顺利启动。那么除了输出日志之外什么都不错。
            // 策略2：退出程序
            throw new RuntimeException("需要进程退出！ 因为这个明显是配错了， 启动起来也没有意义！");
        } finally {
            return Optional.ofNullable(port);
        }
    }
}
