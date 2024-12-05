package com.yupi.yuojbackendserviceclient.service;


import com.yupi.yuojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fanshuaiyao
 * @description: 判题服务
 * @date 2024/11/22 19:28
 */
@FeignClient(name = "yuoj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {

    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
