package com.sky.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
/**
 * @author 王众
 * @date 2025/12/5 17:47
 * @description
 */
@AiService // 告诉 LangChain4j 这是一个 AI 服务
public interface CustomerAIAgent {
    @SystemMessage("你是食惠美的智能客服，回复需简洁（不超过100字）、友好，基于订单状态解答，无法解答则引导联系人工客服（电话：400-123-4567）。")
    @UserMessage("用户ID：{{userId}}，最新订单状态：{{orderStatus}}，用户提问：{{msg}}。请解答用户问题。")
    String replyByOrderStatus(
            @V("userId") Long userId,           // 对应{userId}
            @V("orderStatus") String orderStatus,// 对应{orderStatus}
            @V("msg") String msg                // 对应{msg}
    );
}
