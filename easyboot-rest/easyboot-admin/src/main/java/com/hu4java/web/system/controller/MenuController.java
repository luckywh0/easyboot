package com.hu4java.web.system.controller;

import com.hu4java.base.annotation.Log;
import com.hu4java.base.enums.LogType;
import com.hu4java.base.request.RemoveRequest;
import com.hu4java.base.request.ViewRequest;
import com.hu4java.base.result.Result;
import com.hu4java.system.condition.MenuCondition;
import com.hu4java.system.entity.Menu;
import com.hu4java.system.enums.MenuType;
import com.hu4java.system.service.MenuService;
import com.hu4java.web.system.request.MenuListRequest;
import com.hu4java.web.system.request.MenuSaveRequest;
import com.hu4java.web.system.request.MenuUpdateRequest;
import com.hu4java.web.system.response.MenuTreeTableResponse;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统管理-菜单
 * 菜单管理
 * @author hu4java
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MapperFacade mapperFacade;

    /**
     * 菜单列表
     * @return          菜单数据
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:view")
    public Result<List<MenuTreeTableResponse>> list(MenuListRequest request) {
        List<Menu> menuList;
        if (StringUtils.isNotBlank(request.getTitle())) {
            MenuCondition condition = mapperFacade.map(request, MenuCondition.class);
            menuList = menuService.listTreeByCondition(condition);
        } else {
            menuList = menuService.listTreeByPid(0L);
        }
        List<MenuTreeTableResponse> list = mapperFacade.mapAsList(menuList, MenuTreeTableResponse.class);
        return Result.success(list);
    }

    /**
     * 根据id查询
     * @param request   查询id
     * @return
     */
    @GetMapping("/detail")
    @RequiresPermissions("sys:menu:view")
    public Result<MenuUpdateRequest> detail(@Validated ViewRequest request) {
        Menu menu = menuService.getById(request.getId());
        if (null == menu) {
            return Result.error("菜单数据不存在");
        }
        MenuUpdateRequest response = mapperFacade.map(menu, MenuUpdateRequest.class);
        return Result.success(response);
    }

    /**
     * 保存菜单
     * @param request 保存数据
     * @return
     */
    @PostMapping("/save")
    @Log(desc = "保存菜单", type = LogType.SAVE)
    @RequiresPermissions("sys:menu:save")
    public Result<Void> save(@RequestBody @Validated MenuSaveRequest request) {
        if (!request.getType().equals(MenuType.BUTTON.getType())) {
            Menu exist = menuService.getByRouteName(request.getRouteName());
            if (null != exist) {
                return Result.error("路由名已存在");
            }
        }
        Menu menu = mapperFacade.map(request, Menu.class);
        menuService.save(menu);
        return Result.success();
    }

    /**
     * 更新菜单
     * @param request 更新数据
     * @return
     */
    @PostMapping("/update")
    @Log(desc = "更新菜单", type = LogType.UPDATE)
    @RequiresPermissions("sys:menu:update")
    public Result<Void> update(@RequestBody @Validated MenuUpdateRequest request) {
        if (!request.getType().equals(MenuType.BUTTON.getType())) {
            Menu exist = menuService.getByRouteName(request.getRouteName());
            if (null != exist && !exist.getId().equals(request.getId())) {
                return Result.error("路由名已存在");
            }

        }
        Menu menu = mapperFacade.map(request, Menu.class);
        menuService.update(menu);
        return Result.success();
    }

    /**
     * 删除菜单
     * @param request   数据id
     * @return
     */
    @PostMapping("/remove")
    @Log(desc = "删除菜单", type = LogType.REMOVE)
    @RequiresPermissions("sys:menu:remove")
    public Result<Menu> remove(@RequestBody @Validated RemoveRequest request) {
        List<Menu> menuList = menuService.listByPid(request.getId());
        if (menuList.size() > 0) {
            return Result.error("请先删除下级菜单");
        }
        Menu menu = menuService.getById(request.getId());
        if (null != menu) {
            menuService.removeById(request.getId());
        }

        return Result.success(menu);
    }

}