package core.service;

import java.io.Serializable;
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
     * 保存实体
     * @param entity
     */
    public void save(E entity);

    /**
     * 删除实体
     * @param entity
     */
    public void delete(E entity);

    /**
     * 根据实体ID进行批量删除
     * @param id
     * @return
     */
    public boolean deleteById(Serializable... id);

    /**
     * 更新实体
     * @param entity
     */
    public void update(E entity);

    /**
     * 根据实体ID获取单个实体
     * @param id
     * @return
     */
    public E get(Serializable id);

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
