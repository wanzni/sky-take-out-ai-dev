package com.sky.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
/**
 * @author 王众
 * @date 2025/12/5 17:50
 * @description
 */
@AiService
public interface RecommendAIAgent {
    @SystemMessage("你是外卖平台的推荐助手，仅返回菜品名称，用逗号分隔，不超过5款，无需额外说明。")
    @UserMessage("用户偏好{{flavorTag}}口味，请推荐适合的外卖菜品。")
    String recommendByFlavor(@V("flavorTag") String flavorTag);
}
