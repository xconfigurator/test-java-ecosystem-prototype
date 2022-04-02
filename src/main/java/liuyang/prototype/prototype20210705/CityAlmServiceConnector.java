package liuyang.prototype.prototype20210705;

import liuyang.prototype.prototype20210705.common.Vendors;
import liuyang.prototype.prototype20210705.common.connector.ConnInfo;
import liuyang.prototype.prototype20210705.common.connector.ServiceConnector;

import java.util.Optional;

/*
* 使用抽象出ServiceConnector类之后的写法。
 */
public class CityAlmServiceConnector extends ServiceConnector<CityAlmInterface> {

    @Override
    public Optional<CityAlmInterface> adaptVendor(ConnInfo connInfo) {
        Optional<CityAlmInterface> result = Optional.empty();
        String wsdlLocation = connInfo.getWsdlLocation();
        Vendors vendor = connInfo.getVendor();

        if (Vendors.HBFEC == connInfo.getVendor()) {
            result = getPort(wsdlLocation, "liuyang.prototype.prototype20210705.adapter.hbfec.CityAlmServiceAdapter");
        }

        if (Vendors.HYTERA == connInfo.getVendor()){
            result = getPort(wsdlLocation, "liuyang.prototype.prototype20210705.adapter.hytera.CityAlmServiceAdapter");
        }

        return result;
    }
}
