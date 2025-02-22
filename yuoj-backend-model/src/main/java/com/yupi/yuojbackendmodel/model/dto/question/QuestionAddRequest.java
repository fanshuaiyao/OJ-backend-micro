package com.yupi.yuojbackendmodel.model.dto.question;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description: TODO
 * @author fanshuaiyao
 * @date 2024/11/17 00:19
 * @version 1.0
 */
@Data
public class QuestionAddRequest implements Serializable {
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 标签列表（json 数组）
     */
    @NotEmpty(message = "标签列表不能为空")
    private List<String> tags;

    /**
     * 题目答案
     */
    @NotBlank(message = "题目答案不能为空")
    private String answer;

    /**
     * 判题用例（json 数组）
     */
    @NotEmpty(message = "判题用例列表不能为空")
    @Valid
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置（json 对象）
     */
    @NotNull(message = "判题配置不能为空")
    @Valid
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}