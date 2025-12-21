package com.sky.service;


import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
/**
 * @author 王众
 * @date 2025/12/5 21:13
 * @description
 */
@AiService
public interface DishAIAgent {
    @SystemMessage("你是外卖平台的菜品文案师，生成的文案需口语化、突出卖点，适合外卖展示，每条不超过50字，返回3条用换行分隔。")
    // 模板中使用命名占位符 {dishName}/{ingredients} 等
    @UserMessage("菜品名：{{dishName}}，食材：{{ingredients}}，口味：{{flavors}}，价格：{{price}}元，请生成3条菜品介绍文案。")
    String generateContent(
            @V("dishName") String dishName,     // 对应{dishName}
            @V("ingredients") String ingredients,// 对应{ingredients}
            @V("flavors") String flavors,        // 对应{flavors}
            @V("price") Double price             // 对应{price}
    );
}
