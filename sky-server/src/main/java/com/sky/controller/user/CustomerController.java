package com.sky.controller.user;


/**
 * @author 王众
 * @date 2025/12/5 16:42
 * @description
 */

import com.sky.context.BaseContext;
import com.sky.result.Result;
import com.sky.service.impl.AICustomerServiceImpl;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户端AI客服接口（复用原项目返回规范）
 */
@RestController
@RequestMapping("/user/customer")
public class CustomerController {
    @Resource
    private AICustomerServiceImpl aiCustomerService;

    /**
     * AI回复用户咨询
     */
    @PostMapping("/ai/reply")
    @ApiOperation("AI智能回复")
    public Result<String> reply(@RequestBody Map<String, String> params) {
        // 1. 从 ThreadLocal 中获取当前登录用户的 ID
        // 前提：拦截器已经解析 Token 并存入 BaseContext
        Long userId = BaseContext.getCurrentId();

        // 2. 获取参数
        String msg = params.get("msg");

        // 3. 调用 Service
        String reply = aiCustomerService.replyUserMsg(userId, msg);

        return Result.success(reply);
    }

}
