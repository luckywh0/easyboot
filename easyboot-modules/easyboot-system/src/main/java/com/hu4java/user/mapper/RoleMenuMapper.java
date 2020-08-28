package com.hu4java.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hu4java.base.mapper.BaseMapper;
import com.hu4java.user.entity.RoleMenu;
import org.springframework.stereotype.Repository;

/**
 * @author hu4java
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 根据角色id删除
     * @param roleId    角色id
     * @return          影响行数
     */
    default int deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleId);
        return delete(queryWrapper);
    }

    /**
     * 根据菜单id删除
     * @param menuId    菜单id
     * @return          影响行数
     */
    default int deleteByMenuId(Long menuId) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getMenuId, menuId);
        return delete(queryWrapper);
    }
}