package core.service;

import java.util.List;

/**
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-27
 * @email yongjie.teng@foxmail.com
 * @package core.service
 * @project teemo
 */
public interface Service<E> {
    /**
     * 查询所有实体
     * @return 实体列表
     */
    public List<E> findAll();

    /**
     * 查询实体所有数目
     * @return 实体所有数目
     */
    public Long countAll();
}
