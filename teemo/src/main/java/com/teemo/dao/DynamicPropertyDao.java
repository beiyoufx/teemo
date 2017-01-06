package com.teemo.dao;

import com.teemo.entity.DynamicProperty;
import core.dao.BaseDao;
import core.util.StringUtil;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author yongjie.teng
 * @date 16-11-8 下午5:34
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.dao
 */
@Repository
public class DynamicPropertyDao extends BaseDao<DynamicProperty> {

    /**
     * 部分更新动态属性表字段
     * 只更新以下字段：dynamicPropertyValue/description/modifyTime/version
     * @param dynamicProperty 待更新实体
     */
    public void mergeDynamicProperty(DynamicProperty dynamicProperty) {
        StringBuilder sb = new StringBuilder();
        sb.append("update " + DynamicProperty.class.getName() + " e set ");

        String[] props = new String[2];
        Object[] values = new Object[2];

        int pos = 0;
        // 拼接需要更新的字段属性名
        if (StringUtil.isNotEmpty(dynamicProperty.getDynamicPropertyValue())) {
            props[pos] = "dynamicPropertyValue";
            values[pos] = dynamicProperty.getDynamicPropertyValue();
            sb.append(props[pos] + " = :v_" + props[pos] + ",");
            pos++;
        }

        props[pos] = "description";
        values[pos] = dynamicProperty.getDescription();
        sb.append(props[pos] + " = :v_" + props[pos] + ",");

        // 拼接固定语句
        sb.append("modifyTime = now(), version = version + 0.1 where id = " + dynamicProperty.getId());

        Query query = getSession().createQuery(sb.toString());
        // 拼接需要更新的字段属性值
        for (int i = 0; i <= pos; i++) {
            query.setParameter("v_" + props[i], values[i]);
        }
        query.executeUpdate();
    }
}
