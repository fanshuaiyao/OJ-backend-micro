package com.yupi.yuojbackendjudgeservice.judge.codesandbox;

import com.yupi.yuojbackendmodel.model.codsandbox.ExecuteCodeRequest;
import com.yupi.yuojbackendmodel.model.codsandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口的定义
 */
public interface CodeSandBox {
    /**
     * 执行代码
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);

    // 扩展 可以增加一个查看沙箱状态的接口
}
