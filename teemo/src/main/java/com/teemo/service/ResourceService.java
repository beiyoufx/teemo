/**
 * Copyright (c) 2016- https://github.com/beiyoufx
 *
 * Licensed under the GPL-3.0
 */
package com.teemo.service;

import com.teemo.dao.ResourceDao;
import com.teemo.entity.*;
import core.service.BaseService;
import core.support.Condition;
import core.support.SearchRequest;
import core.support.search.Searchable;
import core.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yongjie.teng
 * @date 16-11-22 上午9:31
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.service
 */
@Service
public class ResourceService extends BaseService<Resource> {
    @javax.annotation.Resource
    private PermissionService permissionService;
    @javax.annotation.Resource
    private AuthorizationService authorizationService;
    private ResourceDao resourceDao;
    @javax.annotation.Resource
    public void setResourceDao(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
        this.dao = resourceDao;
    }

    /**
     * 得到真实的资源标识  即 上级资源标识:子级资源标识
     * @param resource 目标资源
     * @return String
     */
    public String findActualResourceKey(Resource resource) {

        if(resource == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder(resource.getResourceKey());

        boolean hasKey = !StringUtil.isEmpty(resource.getResourceKey());

        Resource parent = get(resource.getParentId());
        while(parent != null) {
            if(!StringUtil.isEmpty(parent.getResourceKey())) {
                sb.insert(0, parent.getResourceKey() + ":");
                hasKey = true;
            }
            parent = get(parent.getParentId());
        }

        // 如果用户没有声明资源标识，并且没有上级资源标识，那么就为空
        if(!hasKey) {
            return "";
        }

        // 删除末尾的:
        int length = sb.length();
        if(length > 0 && sb.lastIndexOf(":") == length - 1) {
            sb.deleteCharAt(length - 1);
        }

        // 如果有子级资源，最后拼一个*
        if (!findChildren(resource.getId()).isEmpty()) {
            sb.append(":*");
        }

        return sb.toString();
    }

    /**
     * 获取根资源ID
     */
    public Long getRootResourceId() {
        return resourceDao.getRootResourceId();
    }

    /**
     * 获取资源节点
     * @return 资源节点
     */
    public List<ResourceNode> findResourceNode(Role role) {
        List<ResourceNode> nodes = new ArrayList<ResourceNode>();

        if (role != null) {
            Set<String> permissions = authorizationService.findPermissionsStr(role);
            Long rootId = getRootResourceId();
            List<Resource> resources = findChildren(rootId);

            for (Resource resource : resources) {
                ResourceNode node = convertToResourceNode(resource, permissions);
                if (node != null) {
                    nodes.add(node);
                }
            }
        }

        return nodes;
    }

    /**
     * 根据父资源ID获取子资源
     * @param parentId 父资源ID
     * @return 子资源列表
     */
    public List<Resource> findChildren(Long parentId) {
        Searchable searchable = SearchRequest.newSearchRequest();
        searchable.addSearchFilter(Condition.newCondition("parentId", parentId));
        return find(searchable);
    }

    /**
     * 递归生成资源树
     * @param resource 资源
     * @param actualPermissions 角色对资源实际拥有的权限
     * @return 资源节点
     */
    public ResourceNode convertToResourceNode(Resource resource, Set<String> actualPermissions) {
        List<Resource> resources = findChildren(resource.getId());
        ResourceNode node = new ResourceNode(resource.getId(), resource.getResourceKey(), resource.getResourceValue());

        // 当前资源没有子资源
        if (resources == null || resources.isEmpty()) {
            // 既没有子资源有没有资源标识符的资源直接过滤掉，菜单也不做授权展示
            if (StringUtil.isEmpty(resource.getResourceKey()) || ResourceType.menu.equals(resource.getType())) {

                return null;
            } else {
                // 增加操作权限状态
                List<ResourceNode.PermissionState> states = new ArrayList<ResourceNode.PermissionState>();
                List<Permission> permissions = permissionService.findAll();
                String actualResourceKey = findActualResourceKey(resource);

                for (Permission permission : permissions) {
                    if (ResourceType.entity.equals(resource.getType())) {// 实体类型的资源可以进行CRUD操作
                        // 判定用户是否有操作该资源的权限
                        Boolean authorized = hasPermission(actualResourceKey + ":" + permission.getPermissionKey(), actualPermissions);
                        states.add(node.new PermissionState(permission, authorized));
                    } else if (ResourceType.view.equals(resource.getType())) { // 视图类资源只有view权限
                        if (ResourceType.view.name().equals(permission.getPermissionKey())) {
                            Boolean authorized = hasPermission(actualResourceKey + ":" + permission.getPermissionKey(), actualPermissions);
                            states.add(node.new PermissionState(permission, authorized));
                        }
                    }
                }
                node.setPermissionStates(states);

                return node;
            }
        } else {
            List<ResourceNode> childNodes = new ArrayList<ResourceNode>();
            for (Resource childResource : resources) {
                ResourceNode childNode = convertToResourceNode(childResource, actualPermissions);
                if (childNode != null) {
                    childNodes.add(childNode);
                }
            }
            // 如果没有子节点，删除该授权项
            if (childNodes.isEmpty()) {
                node = null;
            } else {
                node.setChildren(childNodes);
            }

            return node;
        }
    }

    /**
     * 判定角色是否对资源拥有权限
     * @param permission 待判定权限
     * @param actualPermissions 角色对资源实际拥有的权限
     * @return 角色是否对资源拥有权限
     */
    private boolean hasPermission(String permission, Set<String> actualPermissions) {
        return actualPermissions.contains(permission);
    }

    /**
     * 获取主菜单
     * @return 菜单列表
     */
    public List<Menu> findMenu(User user) {
        List<Menu> menus = new ArrayList<Menu>();

        Set<Role> roles = user.getRoles();
        if (user != null && roles != null) {
            // 先组装用户的权限列表
            Set<String> permissions = new HashSet<String>();
            for (Role role : roles) {
                Set<String> tmpPermissions = authorizationService.findPermissionsStr(role);
                if (!tmpPermissions.isEmpty()) {
                    permissions.addAll(tmpPermissions);
                }
            }
            Long rootId = getRootResourceId();
            List<Resource> resources = findChildren(rootId);

            // 资源转化成菜单
            for (Resource resource : resources) {
                Menu menu = convertToMenu(resource, permissions);
                if (menu != null) {
                    menus.add(menu);
                }
            }
        }

        return menus;
    }

    /**
     * 递归生成菜单树
     * @param resource 资源
     * @param actualPermissions 角色对资源实际拥有的权限
     * @return 菜单
     */
    public Menu convertToMenu(Resource resource, Set<String> actualPermissions) {
        List<Resource> resources = findChildren(resource.getId());
        Menu menu = new Menu();
        menu.setMenuKey(resource.getResourceKey());
        menu.setMenuValue(resource.getResourceValue());
        menu.setUrl(resource.getUrl());
        menu.setMenuIcon(resource.getMenuIcon());
        menu.setSequence(resource.getSequence());

        List<Menu> childMenus = new ArrayList<Menu>();

        // 当前资源没有子资源，所有人可见
        if (resources == null || resources.isEmpty()) {

            return menu;
        } else { // 有子资源，根据子资源进行权限鉴定
            boolean authorized = false;
            for (Resource childResource : resources) {
                // 只将资源类型为菜单的资源添加到菜单列表中
                if (ResourceType.menu.equals(childResource.getType())) {
                    Menu childMenu = convertToMenu(childResource, actualPermissions);
                    if (childMenu != null) {
                        childMenus.add(childMenu);
                        authorized = true;
                    }
                } else {
                    if (ResourceType.view.equals(childResource.getType()) || ResourceType.entity.equals(childResource.getType())) {// 实体和视图可以配置权限
                        String actualResourceKey = findActualResourceKey(childResource);
                        authorized = hasPermission(actualResourceKey + ":" + ResourceType.view.name(), actualPermissions) ? true : authorized;
                    }
                }
            }
            // 子级资源鉴权通过，增加子级菜单
            if (authorized) {
                menu.setChildren(childMenus);
            } else { // 子级资源鉴权不通过，将当前菜单删除
                menu = null;
            }
            return menu;
        }
    }
}
