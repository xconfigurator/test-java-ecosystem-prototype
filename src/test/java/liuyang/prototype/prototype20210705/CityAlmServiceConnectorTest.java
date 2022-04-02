package liuyang.prototype.prototype20210705;

import liuyang.prototype.prototype20210705.common.Vendors;
import liuyang.prototype.prototype20210705.common.connector.ConnInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CityAlmServiceConnectorTest {

    @Test
    void test() {
        // 公共链接
        CityAlmServiceConnector connector = new CityAlmServiceConnector();

        // 从表中读取对端机器的信息
        List<ConnInfo> connInfos = new ArrayList<>();

        ConnInfo info1 = new ConnInfo();
        info1.setVendor(Vendors.HBFEC);
        info1.setWsdlLocation("xxxxx1");
        //connInfos.add(info1);

        ConnInfo info2 = new ConnInfo();
        info2.setVendor(Vendors.HYTERA);
        info2.setWsdlLocation("xxxxx2");
        connInfos.add(info2);

        // 调用
        List<CityAlmInterface> ports = connector.getPorts(connInfos);

        // 试着调用
        if (null != ports) {
            ports.stream().forEach(service -> log.info(service.foo("hey!")));
        }
    }

}
