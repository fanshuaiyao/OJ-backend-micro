package com.yupi.yuojbackendmodel.model.dto.user;

import com.yupi.yuojbackendmodel.model.Anno.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *@ClassName UserRegisterRequest
 *@Description 用户注册请求入参实体
 *@Author fanshuaiyao
 *@Date 2025/2/17 15:44
**/
@Data
@PasswordMatches
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 4, max = 15, message = "用户账号长度范围为4-15字符")
    private String userAccount;

    @NotBlank(message = "用户密码不能为空")
    @Size(min = 4, max = 15, message = "用户密码长度为4-15字符")
    private String userPassword;

    @NotBlank(message = "确认密码不能为空")
    @Size(min = 4, max = 15, message = "用户密码长度为4-15字符")
    private String checkPassword;
}
