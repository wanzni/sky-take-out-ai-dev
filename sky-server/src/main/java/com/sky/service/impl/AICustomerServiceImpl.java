package com.sky.service.impl;


import com.sky.mapper.OrderMapper;
import com.sky.service.CustomerAIAgent;
import com.sky.vo.OrderVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author 王众
 * @date 2025/12/5 16:34
 * @description
 */

/**
 * AI智能客服实现类（联动原项目订单数据）
 */
@Service
public class AICustomerServiceImpl {

    @Resource
    private OrderMapper orderMapper;

    @Resource // 注入 LangChain4j 自动生成的 AI 代理
    private CustomerAIAgent customerAiAgent;


    public String replyUserMsg(Long userId, String msg) {
        // 1. 查数据库逻辑
        OrderVO orderVO = orderMapper.getLatestOrder(userId);
        String orderStatus = orderVO == null ? "无待处理订单" : orderVO.getStatus().toString();

        // 2. 调用 AI 代理接口
        return customerAiAgent.replyByOrderStatus(userId, orderStatus, msg);
    }
}
