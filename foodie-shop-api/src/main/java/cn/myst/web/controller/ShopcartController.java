package cn.myst.web.controller;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumRedisKeys;
import cn.myst.web.pojo.bo.ShopcartBO;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.JsonUtils;
import cn.myst.web.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ziming.xing
 * Create Date：2021/6/27
 */
@Slf4j
@Api(value = "购物车", tags = "购物车的相关接口")
@RestController
@RequestMapping("shopcart")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ShopcartController {
    private final RedisOperator redisOperator;

    @ApiOperation(value = "添加购物车", notes = "添加购物车")
    @PostMapping("add")
    public IMOOCJSONResult add(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(value = "购物车BO", required = true)
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        // 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        // 需要判断当前购物车中包含已经存在的商品，如果存在则累加商品数量
        String shopCartKey = EnumRedisKeys.SHOP_CART.key + ":" + userId;
        String json = redisOperator.get(shopCartKey);
        List<ShopcartBO> shopCartList;
        if (StringUtils.isNotBlank(json)) {
            // redis中已经有购物车了
            shopCartList = JsonUtils.jsonToList(json, ShopcartBO.class);
            // 判断商品中是否存在已有商品，如果有的话counts累加
            AtomicBoolean isHaving = new AtomicBoolean(false);
            Optional.ofNullable(shopCartList).orElse(new ArrayList<>())
                    .forEach(sc -> {
                        if (Objects.equals(sc.getSpecId(), shopcartBO.getSpecId())) {
                            sc.setBuyCounts(sc.getBuyCounts() + shopcartBO.getBuyCounts());
                            isHaving.set(true);
                        }
                    });
            // isHaving为false，购物车里不包含新添加的商品，
            if (!isHaving.get() && Objects.nonNull(shopCartList)) {
                shopCartList.add(shopcartBO);
            }
        } else {
            // redis中没有购物车
            shopCartList = new ArrayList<>();
            // 直接添加到购物车中
            shopCartList.add(shopcartBO);
        }
        // 覆盖现有redis中的购物车
        redisOperator.set(shopCartKey, JsonUtils.objectToJson(shopCartList));

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品")
    @DeleteMapping("del")
    public IMOOCJSONResult del(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(value = "商品规格id", required = true)
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        // 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品
        String shopCartKey = EnumRedisKeys.SHOP_CART.key + ":" + userId;
        String json = redisOperator.get(shopCartKey);
        List<ShopcartBO> shopCartList = null;
        if (StringUtils.isNotBlank(json)) {
            // redis中已经有购物车了
            shopCartList = JsonUtils.jsonToList(json, ShopcartBO.class);
            if (!CollectionUtils.isEmpty(shopCartList)) {
                shopCartList.removeIf(sc -> Objects.equals(sc.getSpecId(), itemSpecId));
            }
        }
        // 覆盖现有redis中的购物车
        redisOperator.set(shopCartKey, JsonUtils.objectToJson(shopCartList));

        return IMOOCJSONResult.ok();
    }
}
