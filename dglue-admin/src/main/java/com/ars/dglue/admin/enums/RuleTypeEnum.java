package com.ars.dglue.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-24.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Getter
@AllArgsConstructor
public enum RuleTypeEnum {
    /**
     * 1 逻辑校验 2 逻辑处理
     */
    VALID(1, "逻辑校验"), PROCESSING(2, "逻辑处理");

    private Integer code;

    private String desc;

    public static boolean isExist(Integer code) {
        Integer ruleType = Arrays.stream(RuleTypeEnum.values()).map(RuleTypeEnum::getCode)
                .filter(integer -> integer.equals(code)).findFirst().orElse(null);
        return null != ruleType;
    }
}
