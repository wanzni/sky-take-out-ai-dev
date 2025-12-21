package com.sky.service.impl;


import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.service.RecommendAIAgent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jakarta.annotation.Resource;

/**
 * @author 王众
 * @date 2025/12/5 11:36
 * @description
 */
@Service
public class AIRecommendServiceImpl {

    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource // 注入 AI 代理
    private RecommendAIAgent recommendAiAgent;


    public List<String> recommendDish(Long userId) {
        // 1. 业务逻辑：查订单
        List<Orders> ordersList = orderMapper.listByUserId(userId);

        // 2. 业务逻辑：兜底策略
        if (ordersList == null || ordersList.isEmpty()) {
            return Arrays.asList("麻辣香锅", "番茄炒蛋盖饭", "香辣鸡腿堡");
        }

        // 3. 业务逻辑：提取口味
        String flavorTag = extractTasteTag(ordersList);

        // 4. 业务逻辑：存 Redis
        redisTemplate.opsForValue().set("user:prefer:" + userId, flavorTag, 7, TimeUnit.DAYS);

        // 5. 调用 AI 代理获取结果
        String recommendStr = recommendAiAgent.recommendByFlavor(flavorTag);

        // 6. 解析结果
        return Arrays.asList(recommendStr.split(","));
    }

    // extractTasteTag 方法保持不变...
    private String extractTasteTag(List<Orders> ordersList) {
        // ... (原代码保持不变)
        Map<String, Integer> flavorCountMap = new HashMap<>();
        for (Orders order : ordersList) {
            List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(order.getId());
            for (OrderDetail detail : orderDetails) {
                String dishFlavor = detail.getDishFlavor();
                if (dishFlavor != null && !dishFlavor.isEmpty()) {
                    String[] flavors = dishFlavor.split(",");
                    for (String flavor : flavors) {
                        flavor = flavor.trim();
                        if (!flavor.isEmpty()) {
                            flavorCountMap.put(flavor, flavorCountMap.getOrDefault(flavor, 0) + 1);
                        }
                    }
                }
            }
        }
        if (flavorCountMap.isEmpty()) return "微辣";
        return flavorCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("微辣");
    }
}
