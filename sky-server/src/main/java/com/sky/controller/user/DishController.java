package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;

import com.sky.service.impl.AIRecommendServiceImpl;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private AIRecommendServiceImpl aiRecommendService;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {

        //构造redis中的key，规则：dish_分类id
        String key = "dish_" + categoryId;
        //查询redis中是否存在菜品数据
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(list != null && list.size() > 0){
            //如果存在，直接返回
            return Result.success(list);
        }

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品
        //如果不存在，根据分类id查询菜品数据，并缓存到redis
        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, list);

        return Result.success(list);
    }
    /**
     * AI推荐菜品（贴合原项目鉴权逻辑：从请求头获取userId）
     */
    @GetMapping("/ai/recommend")
    @ApiOperation("AI智能推荐菜品")
    public Result<List<String>> recommend() {
        // 1. 从 ThreadLocal 获取当前登录用户 ID
        // (拦截器已经解析了 Token 并放入了 BaseContext，这里直接取即可)
        Long userId = BaseContext.getCurrentId();

        // 2. 打印日志方便调试 (可选)
        log.info("用户端请求AI推荐，当前用户ID: {}", userId);

        // 3. 调用 Service
        List<String> recommendList = aiRecommendService.recommendDish(userId);

        return Result.success(recommendList);
    }
}
