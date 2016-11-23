package com.teemo.core.util;

import com.teemo.entity.DynamicProperty;
import com.teemo.service.DynamicPropertyService;
import core.util.SpringUtil;

/**
 * @author yongjie.teng
 * @date 16-11-8 下午5:38
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.core.util
 */
public final class DPUtil {
    private static DynamicPropertyService dynamicPropertyService;

    public static String getDynamicProperty(String key) {
        if (dynamicPropertyService == null) {
            dynamicPropertyService = SpringUtil.getBean("dynamicPropertyService");
        }
        DynamicProperty dynamicProperty = dynamicPropertyService.get("key", key);
        if (dynamicProperty != null) {
            return dynamicProperty.getValue();
        }
        return null;
    }
}
