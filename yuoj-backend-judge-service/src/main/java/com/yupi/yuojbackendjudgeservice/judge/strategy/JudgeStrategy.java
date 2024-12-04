package com.yupi.yuojbackendjudgeservice.judge.strategy;


import com.yupi.yuojbackendmodel.model.codsandbox.JudgeInfo;

/**
 * @author fanshuaiyao
 * @description: TODO
 * @date 2024/11/22 20:31
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
