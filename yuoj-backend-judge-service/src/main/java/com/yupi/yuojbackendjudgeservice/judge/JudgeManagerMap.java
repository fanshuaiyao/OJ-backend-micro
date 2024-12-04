package com.yupi.yuojbackendjudgeservice.judge;


import com.yupi.yuojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.yupi.yuojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.yupi.yuojbackendjudgeservice.judge.strategy.JudgeContext;
import com.yupi.yuojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.yupi.yuojbackendmodel.model.codsandbox.JudgeInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fanshuaiyao
 * @description: 尽量简化调用方的操作  判题管理
 * @date 2024/11/23 15:11
 */
@Service
public class JudgeManagerMap {

    private Map<String, JudgeStrategy> judgeStrategyMap = new HashMap<>();

     JudgeManagerMap() {
        judgeStrategyMap.put("default", new DefaultJudgeStrategy());
        judgeStrategyMap.put("java",new JavaLanguageJudgeStrategy());
    }

    public void add(String strategy, JudgeStrategy judgeStrategy){
        judgeStrategyMap.put(strategy, judgeStrategy);
    }
    /**
     * 执行判题
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        JudgeStrategy judgeStrategy =  judgeStrategyMap.get("default");
        String language = judgeContext.getQuestionSubmit().getLanguage();
        if (judgeStrategyMap.containsKey(language)){
            judgeStrategy =  judgeStrategyMap.get(language);
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
