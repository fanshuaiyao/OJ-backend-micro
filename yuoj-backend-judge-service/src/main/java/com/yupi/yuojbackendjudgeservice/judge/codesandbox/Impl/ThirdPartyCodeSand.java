package com.yupi.yuojbackendjudgeservice.judge.codesandbox.Impl;

import com.yupi.yuojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.yupi.yuojbackendmodel.model.codsandbox.ExecuteCodeRequest;
import com.yupi.yuojbackendmodel.model.codsandbox.ExecuteCodeResponse;

/**
 * @author fanshuaiyao
 * @description: 第三方的沙箱
 * @date 2024/11/22 17:14
 */
public class ThirdPartyCodeSand implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}

