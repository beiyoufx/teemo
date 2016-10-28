package com.teemo.core.entity;

/**
 * 实现该接口表示实体想要逻辑删除
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-21
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.core.entity
 * @project teemo
 */
public interface LogicDeletable {
    public Boolean getDeleted();
    public void setDeleted(Boolean deleted);

    /**
     * 标记实体为已删除
     */
    public void markDeleted();
}
