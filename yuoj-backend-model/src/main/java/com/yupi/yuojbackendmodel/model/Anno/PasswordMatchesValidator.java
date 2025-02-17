package com.yupi.yuojbackendmodel.model.Anno;


import com.yupi.yuojbackendmodel.model.dto.user.UserRegisterRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 * @className: PasswordMatchesValidator
 * @author: fanshuaiyao
 * @description: 用户注册密码一致性的检验器
 * @date: 2025/2/17 16:33
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegisterRequest> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserRegisterRequest request, ConstraintValidatorContext context) {
        return request.getUserPassword().equals(request.getCheckPassword());
    }
}
