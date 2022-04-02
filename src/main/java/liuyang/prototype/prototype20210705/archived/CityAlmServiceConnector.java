package liuyang.prototype.prototype20210705.archived;

import liuyang.prototype.prototype20210705.common.Vendors;
import liuyang.prototype.prototype20210705.common.connector.ConnInfo;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 抽象出ServiceConnector之前的写法
 *
 * @param <T> 接口类型
 */
@Slf4j
public class CityAlmServiceConnector<T> {

    // 需要定制化的部分（根据适配的厂商来调用相应的stub）
    public Optional<T> adaptVendor(ConnInfo connInfo) {
        Optional<T> result = Optional.empty();
        String wsdlLocation = connInfo.getWsdlLocation();
        Vendors vendor = connInfo.getVendor();

        if (Vendors.HBFEC == connInfo.getVendor()) {
            result = getPort(wsdlLocation, "liuyang.a.migration.questions.test20210705.adapter.hbfec.CityAlmServiceAdapter");
        }

        if (Vendors.HYTERA == connInfo.getVendor()){
            result = getPort(wsdlLocation, "liuyang.a.migration.questions.test20210705.adapter.hytera.CityAlmServiceAdapter");
        }

        return result;
    }

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
    public Optional<T> getPort(String wsdlLocation, String adapterClassName){
        log.info("wsdlLocation = " + wsdlLocation);
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
        } finally {
            return Optional.ofNullable(port);
        }
    }
}
