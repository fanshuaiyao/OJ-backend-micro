package com.yupi.yuojbackendmodel.model.dto.questionsubmit;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建请求
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {
    /**
     * 编程语言
     */
    @NotBlank(message = "编程语言不能为空")
    private String language;

    /**
     * 用户代码
     */
    @NotBlank(message = "用户代码不能为空")
    private String code;

    /**
     * 题目 id
     */
    @NotNull(message = "题目 id 不能为空")
    private Long questionId;

    private static final long serialVersionUID = 1L;
}