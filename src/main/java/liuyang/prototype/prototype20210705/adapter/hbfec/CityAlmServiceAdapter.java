package liuyang.prototype.prototype20210705.adapter.hbfec;


import liuyang.prototype.prototype20210705.CityAlmInterface;
import liuyang.prototype.prototype20210705.stub.hbfec.CityAlmService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CityAlmServiceAdapter implements CityAlmInterface {

    private CityAlmInterface cityAlmServie;

    public CityAlmServiceAdapter(String wsdlLocation) {
        // 合理就直接new了
        cityAlmServie = new CityAlmService();
    }

    @Override
    public String foo(String param) {
        return cityAlmServie.foo(param);
    }
}
