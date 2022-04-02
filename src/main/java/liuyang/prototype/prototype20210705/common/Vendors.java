package liuyang.prototype.prototype20210705.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liuyang
 * @sice 2021/7/5
 */
@AllArgsConstructor
@Getter
public enum Vendors {
    HBFEC("远东通信"), HYTERA("海能达");
    private final String vendorName;
}
