package cn.myst.web.entity.base;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author ziming.xing
 * Create Date：2022/4/13
 */
public class FoodieShopResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8028383044156335195L;

    public FoodieShopResponse code(HttpStatus status) {
        put("code", status.value());
        return this;
    }

    public FoodieShopResponse message(String message) {
        put("message", message);
        return this;
    }

    public FoodieShopResponse data(Object data) {
        put("data", data);
        return this;
    }

    public FoodieShopResponse success() {
        code(HttpStatus.OK);
        return this;
    }

    public FoodieShopResponse fail() {
        code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public FoodieShopResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
