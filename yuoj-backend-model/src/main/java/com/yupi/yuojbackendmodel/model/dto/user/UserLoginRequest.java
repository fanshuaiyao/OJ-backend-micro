package com.yupi.yuojbackendmodel.model.dto.user;

import lombok.Data;
import org.springframework.cloud.loadbalancer.core.SameInstancePreferenceServiceInstanceListSupplier;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户登录请求
 *
 *
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 4, max = 15, message = "用户账号合法长度为4-15个字符")
    private String userAccount;

    @NotBlank(message = "用户密码不能为空")
    @Size(min = 4, max = 15, message = "用户密码合法长度为4-15个字符")
    private String userPassword;
}
