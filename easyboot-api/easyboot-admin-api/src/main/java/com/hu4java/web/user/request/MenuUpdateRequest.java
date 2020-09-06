package com.hu4java.web.user.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 菜单更新表单参数
 * @author hu4java
 */
@Getter
@Setter
public class MenuUpdateRequest extends MenuFormRequest {
    private static final long serialVersionUID = -7106406021091846454L;

    /** 菜单id*/
    @NotNull(message = "id不能为空")
    private Long id;
}