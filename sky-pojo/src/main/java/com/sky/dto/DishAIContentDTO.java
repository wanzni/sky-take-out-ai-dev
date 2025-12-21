package com.sky.dto;

import lombok.Data;
import java.math.BigDecimal;


/**
 * @author 王众
 * @date 2025/12/5 11:14
 * @description
 */

/**
 * 菜品AI文案生成DTO（贴合苍穹外卖DTO命名规范）
 */
@Data
public class DishAIContentDTO {
    private String dishName;   // 菜品名
    private String ingredients;// 食材
    private String flavors;      // 口味（麻辣/清淡/酸甜）
    private BigDecimal price;  // 价格
}
