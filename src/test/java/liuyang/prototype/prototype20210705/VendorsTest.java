package liuyang.prototype.prototype20210705;

import liuyang.prototype.prototype20210705.common.Vendors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class VendorsTest {

    @Test
    void testEnum() {
        log.info(Vendors.HBFEC.ordinal() + "");
        log.info(Vendors.HBFEC.getVendorName());
        log.info(Vendors.HYTERA.ordinal() + "");
        log.info(Vendors.HYTERA.getVendorName());
    }
}
