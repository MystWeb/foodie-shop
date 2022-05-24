package cn.myst.web.tcc.demo.controller;

import cn.myst.web.tcc.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderController {

    private final OrderService orderService;

    @RequestMapping("handleOrder")
    public String handleOrder(int orderId) {

        try {
            int result = orderService.handleOrder(orderId);

            if (result == 0) return "success";

            return "fail";
        } catch (Exception e) {
            return "fail";
        }

    }
}
