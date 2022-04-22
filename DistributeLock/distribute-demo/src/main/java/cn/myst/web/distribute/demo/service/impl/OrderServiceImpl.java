package cn.myst.web.distribute.demo.service.impl;

import cn.myst.web.distribute.demo.domain.Order;
import cn.myst.web.distribute.demo.domain.OrderItem;
import cn.myst.web.distribute.demo.domain.Product;
import cn.myst.web.distribute.demo.mapper.OrderItemMapper;
import cn.myst.web.distribute.demo.mapper.OrderMapper;
import cn.myst.web.distribute.demo.mapper.ProductMapper;
import cn.myst.web.distribute.demo.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ziming.xing
 * Create Date：2022/4/20
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    //购买商品id
    private final int purchaseProductId = 1;
    //购买商品数量
    private final int purchaseProductNum = 1;
    // 当前用户
    private final String currentUser = "test";

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductMapper productMapper;

    private final PlatformTransactionManager platformTransactionManager;
    private final TransactionDefinition transactionDefinition;

    private final Lock lock = new ReentrantLock();

    @Override
    public int updateBatchSelective(List<Order> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<Order> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public synchronized Integer createOrder() throws Exception {
        Product product;
        // 加锁
        lock.lock();
        try {
            // 获取代码块锁内的事务，因为要事务提交后，再释放锁
            TransactionStatus transactionLock = platformTransactionManager.getTransaction(transactionDefinition);

            // 校验商品
            product = productMapper.selectById(purchaseProductId);
            if (Objects.isNull(product)) {
                // 事务回滚
                platformTransactionManager.rollback(transactionLock);
                throw new Exception("购买商品：" + purchaseProductId + "不存在");
            }

            // 商品当前库存
            Integer currentCount = product.getCount();
            log.info(Thread.currentThread().getName() + "库存数：" + currentCount);

            // 校验库存
            if (purchaseProductNum > currentCount) {
                // 事务回滚
                platformTransactionManager.rollback(transactionLock);
                throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
            }

            // 数据库更新库存
            // 当前时间
            Date now = Date.from(Instant.now());
            LambdaUpdateWrapper<Product> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            String sql = "count = count - " + purchaseProductNum;
            lambdaUpdateWrapper.setSql(sql)
                    .set(Product::getUpdateUser, currentUser)
                    .set(Product::getUpdateTime, now)
                    .eq(Product::getId, product.getId());
            productMapper.update(null, lambdaUpdateWrapper);
            // 手动事务提交
            platformTransactionManager.commit(transactionLock);
        } finally {
            // 释放锁
            lock.unlock();
        }

        // 获取方法体事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        Integer orderId = getOrderId(product);
        // 手动提交事务
        platformTransactionManager.commit(transaction);
        return orderId;
    }

    /**
     * 创建订单、订单商品数据
     *
     * @param product 商品
     * @return 订单id
     */
    private Integer getOrderId(Product product) {
        Date now = Date.from(Instant.now());
        Order order = new Order();
        order.setOrderStatus(1); // 待处理
        order.setReceiverName("test");
        order.setReceiverMobile("13311112222");
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        order.setCreateUser(currentUser);
        order.setCreateTime(now);
        order.setUpdateUser(currentUser);
        order.setUpdateTime(now);

        orderMapper.insert(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser(currentUser);
        orderItem.setCreateTime(now);
        orderItem.setUpdateUser(currentUser);
        orderItem.setUpdateTime(now);

        orderItemMapper.insert(orderItem);

        return order.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer createOrderByJavaCalculation() throws Exception {
        Product product = productMapper.selectById(purchaseProductId);
        if (Objects.isNull(product))
            throw new Exception("购买商品：" + purchaseProductId + "不存在");

        // 商品当前库存
        Integer currentCount = product.getCount();
        // 校验库存
        if (purchaseProductNum > currentCount)
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
        // Java程序计算剩余库存，存在BUG：多线程并发执行会创建2~N个订单
        int leftCount = currentCount - purchaseProductNum;
        // 更新库存
        product.setCount(leftCount);
        product.setUpdateUser(currentUser);
        // 当前时间
        Date now = Date.from(Instant.now());
        product.setUpdateTime(now);
        productMapper.updateById(product);

        return getOrderId(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer createOrderBySQLCalculation() throws Exception {
        Product product = productMapper.selectById(purchaseProductId);
        if (Objects.isNull(product))
            throw new Exception("购买商品：" + purchaseProductId + "不存在");

        // 商品当前库存
        Integer currentCount = product.getCount();
        // 校验库存
        // 多线程并发场景下，所有线程获取的库存数都一致，校验失效
        if (purchaseProductNum > currentCount)
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");

        // 当前时间
        Date now = Date.from(Instant.now());
        // 通过数据库Update语句计算商品库存，通过Update行锁解决并发
        LambdaUpdateWrapper<Product> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        String sql = "count = count - " + purchaseProductNum;
        lambdaUpdateWrapper.setSql(sql)
                .set(Product::getUpdateUser, currentUser)
                .set(Product::getUpdateTime, now)
                .eq(Product::getId, product.getId());
        productMapper.update(null, lambdaUpdateWrapper);

        return getOrderId(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized Integer createOrderBySynchronized() throws Exception {
        Product product = productMapper.selectById(purchaseProductId);
        if (Objects.isNull(product))
            throw new Exception("购买商品：" + purchaseProductId + "不存在");

        // 商品当前库存
        Integer currentCount = product.getCount();
        log.info(Thread.currentThread().getName() + "库存数：" + currentCount);
        // 校验库存
        if (purchaseProductNum > currentCount)
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");

        // 当前时间
        Date now = Date.from(Instant.now());
        // 数据库更新库存
        LambdaUpdateWrapper<Product> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        String sql = "count = count - " + purchaseProductNum;
        lambdaUpdateWrapper.setSql(sql)
                .set(Product::getUpdateUser, currentUser)
                .set(Product::getUpdateTime, now)
                .eq(Product::getId, product.getId());
        productMapper.update(null, lambdaUpdateWrapper);

        return getOrderId(product);
    }

    @Override
    public Integer createOrderByPlatformTransactionManager() throws Exception {
        // 获取事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);

        Product product = productMapper.selectById(purchaseProductId);
        if (Objects.isNull(product)) {
            // 事务回滚
            platformTransactionManager.rollback(transaction);
            throw new Exception("购买商品：" + purchaseProductId + "不存在");
        }

        // 商品当前库存
        Integer currentCount = product.getCount();
        log.info(Thread.currentThread().getName() + "库存数：" + currentCount);
        // 校验库存
        if (purchaseProductNum > currentCount) {
            // 事务回滚
            platformTransactionManager.rollback(transaction);
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
        }

        // 当前时间
        Date now = Date.from(Instant.now());
        // 数据库更新库存
        LambdaUpdateWrapper<Product> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        String sql = "count = count - " + purchaseProductNum;
        lambdaUpdateWrapper.setSql(sql)
                .set(Product::getUpdateUser, currentUser)
                .set(Product::getUpdateTime, now)
                .eq(Product::getId, product.getId());
        productMapper.update(null, lambdaUpdateWrapper);
        // 手动事务提交
        platformTransactionManager.commit(transaction);
        return getOrderId(product);
    }

    @Override
    public Integer createOrderBySynchronizedBlock() throws Exception {
        Product product;
        synchronized (this) {
            // 获取代码块锁内的事务，因为要事务提交后，再释放锁
            TransactionStatus transactionLock = platformTransactionManager.getTransaction(transactionDefinition);

            // 校验商品
            product = productMapper.selectById(purchaseProductId);
            if (Objects.isNull(product)) {
                // 事务回滚
                platformTransactionManager.rollback(transactionLock);
                throw new Exception("购买商品：" + purchaseProductId + "不存在");
            }

            // 商品当前库存
            Integer currentCount = product.getCount();
            log.info(Thread.currentThread().getName() + "库存数：" + currentCount);

            // 校验库存
            if (purchaseProductNum > currentCount) {
                // 事务回滚
                platformTransactionManager.rollback(transactionLock);
                throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
            }

            // 数据库更新库存
            // 当前时间
            Date now = Date.from(Instant.now());
            LambdaUpdateWrapper<Product> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            String sql = "count = count - " + purchaseProductNum;
            lambdaUpdateWrapper.setSql(sql)
                    .set(Product::getUpdateUser, currentUser)
                    .set(Product::getUpdateTime, now)
                    .eq(Product::getId, product.getId());
            productMapper.update(null, lambdaUpdateWrapper);
            // 手动事务提交
            platformTransactionManager.commit(transactionLock);
        }

        // 获取方法体事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        Integer orderId = getOrderId(product);
        // 手动提交事务
        platformTransactionManager.commit(transaction);
        return orderId;
    }

    @Override
    public Integer createOrderByReentrantLock() throws Exception {
        Product product;
        // 加锁
        lock.lock();
        try {
            // 获取代码块锁内的事务，因为要事务提交后，再释放锁
            TransactionStatus transactionLock = platformTransactionManager.getTransaction(transactionDefinition);

            // 校验商品
            product = productMapper.selectById(purchaseProductId);
            if (Objects.isNull(product)) {
                // 事务回滚
                platformTransactionManager.rollback(transactionLock);
                throw new Exception("购买商品：" + purchaseProductId + "不存在");
            }

            // 商品当前库存
            Integer currentCount = product.getCount();
            log.info(Thread.currentThread().getName() + "库存数：" + currentCount);

            // 校验库存
            if (purchaseProductNum > currentCount) {
                // 事务回滚
                platformTransactionManager.rollback(transactionLock);
                throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
            }

            // 数据库更新库存
            // 当前时间
            Date now = Date.from(Instant.now());
            LambdaUpdateWrapper<Product> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            String sql = "count = count - " + purchaseProductNum;
            lambdaUpdateWrapper.setSql(sql)
                    .set(Product::getUpdateUser, currentUser)
                    .set(Product::getUpdateTime, now)
                    .eq(Product::getId, product.getId());
            productMapper.update(null, lambdaUpdateWrapper);
            // 手动事务提交
            platformTransactionManager.commit(transactionLock);
        } finally {
            // 释放锁
            lock.unlock();
        }

        // 获取方法体事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        Integer orderId = getOrderId(product);
        // 手动提交事务
        platformTransactionManager.commit(transaction);
        return orderId;
    }
}
