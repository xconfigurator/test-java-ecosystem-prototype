package liuyang.prototype.prototype20210705.archived;

import liuyang.prototype.prototype20210705.CityAlmInterface;
import liuyang.prototype.prototype20210705.stub.hbfec.CityAlmService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CityAlmInterfaceTest {

    @Test
    void testFoo() {
        Object o = null;
        CityAlmInterface inst = (CityAlmService) o;
        System.out.println("inst = " + inst);
    }

    @Test
    void testBar() {
        Optional<CityAlmInterface> o1 = null;
        System.out.println(o1);

        //Optional<CityAlmInterface> o2 = Optional.of(null);
        //System.out.println(o2);
        // java.lang.NullPointerException

        //CityAlmInterface i = null;
        //Optional<CityAlmInterface> o3 = Optional.of(i);
        //System.out.println(o3);
        // java.lang.NullPointerException
    }

    @Test
    @DisplayName("测试Optional")
    void testOptional() {
        Optional<CityAlmInterface> o1 = Optional.ofNullable(null);
        System.out.println(o1);// Optional.empty
        System.out.println(o1.isPresent());// false

        Optional<CityAlmInterface> o2 = Optional.empty();
        System.out.println(o2);// Optional.empty
        System.out.println(o2.isPresent());// false

        NullPointerException e = Assertions.assertThrows(NullPointerException.class, () -> {
            Optional<CityAlmInterface> o3 = Optional.of(null);
            //System.out.println(o3);
            //System.out.println(o3.isPresent());
        });
        log.error(e.getMessage(), e);
    }

    @Test
    void testPortHbfec() {
        CityAlmServiceConnector<CityAlmInterface> cityAlmServiceConnector = new CityAlmServiceConnector<CityAlmInterface>();
        Optional<CityAlmInterface> port = cityAlmServiceConnector.getPort("http://xxxx1/CityAlmService.wsdl", "liuyang.a.migration.questions.test20210705.adapter.hbfec.CityAlmServiceAdapter");
        if (port.isPresent()) {
            String foo = port.get().foo("hello! ");
            log.info("存根可配置化：" + foo);
        }
    }

    // 这个类并不存在
    @Test
    void testPortHbfecException() {
        CityAlmServiceConnector<CityAlmInterface> cityAlmServiceConnector = new CityAlmServiceConnector<CityAlmInterface>();
        Optional<CityAlmInterface> port = cityAlmServiceConnector.getPort("http://xxxx1/CityAlmService.wsdl", "xxxx");
        if (port.isPresent()) {
            String foo = port.get().foo("hello! ");
            log.info("存根可配置化：" + foo);
        } else {
            log.info("获取port失败");
        }
    }

    // 还需要测试指定wsdl无法访问到的情况。
    @Test
    void testPortHbfecExceptionWSDL() {
        // 需要在pdt-nms环境中建立stub的时候来进行。
    }

    @Test
    void testPortHytera() {
        CityAlmServiceConnector<CityAlmInterface> cityAlmServiceConnector = new CityAlmServiceConnector<CityAlmInterface>();
        Optional<CityAlmInterface> port = cityAlmServiceConnector.getPort("http://xxxx2/CityAlmService.wsdl", "liuyang.a.migration.questions.test20210705.adapter.hytera.CityAlmServiceAdapter");
        if (port.isPresent()) {
            String foo = port.get().foo("hello! ");
            log.info("存根可配置化：" + foo);
        }
    }

    @Test
    void testCollection() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        log.info("#" + String.valueOf(list.size()));
        log.info("#" + list.get(0) + "");
    }
}
