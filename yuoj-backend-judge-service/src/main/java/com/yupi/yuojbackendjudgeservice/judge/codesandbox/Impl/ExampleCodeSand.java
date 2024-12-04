package com.yupi.yuojbackendjudgeservice.judge.codesandbox.Impl;


import com.yupi.yuojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.yupi.yuojbackendmodel.model.codsandbox.ExecuteCodeRequest;
import com.yupi.yuojbackendmodel.model.codsandbox.ExecuteCodeResponse;
import com.yupi.yuojbackendmodel.model.codsandbox.JudgeInfo;
import com.yupi.yuojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.yupi.yuojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @author fanshuaiyao
 * @description: 样例代码沙箱
 * @date 2024/11/22 17:14
 */
public class ExampleCodeSand implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        // todo 也可以作为响应值返回出去让用户看到
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
