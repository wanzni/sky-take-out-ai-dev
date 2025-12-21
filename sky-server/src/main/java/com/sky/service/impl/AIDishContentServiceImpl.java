package com.sky.service.impl;


import com.sky.dto.DishAIContentDTO;
import com.sky.service.DishAIAgent;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author 王众
 * @date 2025/12/5 11:31
 * @description
 */
/**
 * 菜品AI文案实现类（核心：加@AiService+@Service，集成LangChain4j）
 */
@Service
public class AIDishContentServiceImpl {

    // 注入刚才定义的 AI 接口（Spring 会自动注入 LangChain4j 生成的代理对象）
    @Resource
    private DishAIAgent dishAiAgent;

    /**
     * 业务实现方法
     */
    public String generateDishContent(DishAIContentDTO dto) {
        // 调用 AI 代理
        return dishAiAgent.generateContent(
                dto.getDishName(),
                dto.getIngredients(),
                dto.getFlavors(),
                dto.getPrice().doubleValue()
        );
    }
}
