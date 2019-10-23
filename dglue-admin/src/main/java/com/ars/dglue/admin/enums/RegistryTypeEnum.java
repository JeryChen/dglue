package com.ars.dglue.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-22.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Getter
@AllArgsConstructor
public enum RegistryTypeEnum {

    /**
     * auto registry
     */
    AUTO(1, "自动注册"),

    /**
     * manual registry
     */
    MANUAL(2, "手动注册");

    private Integer code;

    private String desc;

    public static boolean isExist(Integer code) {
        Integer registryType = Arrays.stream(RegistryTypeEnum.values()).map(RegistryTypeEnum::getCode)
                .filter(integer -> integer.equals(code)).findFirst().orElse(null);
        return null != registryType;
    }
}
