package cn.myst.web.distribute.demo.service;

import cn.myst.web.distribute.demo.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 商品超卖演示与解决方案
 * 超卖现象：1个商品，库存剩余1，并发销售，出现多个订单
 *
 * @author ziming.xing
 * Create Date：2022/4/20
 */
public interface OrderService extends IService<Order> {

    int updateBatchSelective(List<Order> list);

    int batchInsert(List<Order> list);


    Integer createOrder() throws Exception;

    /**
     * 通过Java程序计算商品库存
     * 多线程并发执行会存在BUG：创建2~N个订单
     * 原因：扣减库存的动作，在程序中进行，在程序中计算剩余库存
     * 并发的场景下，导致库存计算错误
     * 解决方案：
     * 1、通过数据库Update语句计算商品库存，通过Update行锁 解决并发
     */
    Integer createOrderByJavaCalculation() throws Exception;

    /**
     * 通过数据库Update语句计算商品库存，通过Update行锁 解决并发
     * 多线程并发执行会存在BUG：系统中库存变为 -1（负数）
     * 原因：并发校验库存，造成库存充足的假象
     * （多线程并发场景下，所有线程获取的库存数都一致，校验失效）
     * 解决方案：
     * 1、校验库存、扣减库存统一加锁
     * 2、使之成为原子性的操作
     * 3、并发时，只有获得锁的线程才能校验、扣减库存
     * 4、扣减库存结束后，释放锁
     * 5、确保库存不会扣成负数
     */
    Integer createOrderBySQLCalculation() throws Exception;

    /**
     * 概念参考：<a href="https://blog.csdn.net/a745233700/article/details/119923661">https://blog.csdn.net/a745233700/article/details/119923661</a>
     * 通过数据库Update语句计算商品库存，通过Update行锁、悲观锁-Synchronized隐式锁方法 解决并发
     * 多线程并发执行会存在BUG：导致创建2~N个订单
     * 原因：线程1的事务未提交，线程2就开始执行，
     * 解决方案：
     * 1、添加事务锁
     */
    Integer createOrderBySynchronized() throws Exception;

    /**
     * 概念参考：<a href="https://blog.csdn.net/a745233700/article/details/119923661">https://blog.csdn.net/a745233700/article/details/119923661</a>
     * 通过数据库Update语句计算商品库存，
     * 通过Update行锁、悲观锁-Synchronized隐式锁方法体、
     * 平台事务管理器-PlatformTransactionManager，手动控制事务 解决并发
     */
    Integer createOrderByPlatformTransactionManager() throws Exception;

    /**
     * 概念参考：<a href="https://blog.csdn.net/a745233700/article/details/119923661">https://blog.csdn.net/a745233700/article/details/119923661</a>
     * 通过数据库Update语句计算商品库存，
     * 通过Update行锁、悲观锁-Synchronized隐式锁代码块、
     * 平台事务管理器-PlatformTransactionManager，手动控制事务 解决并发
     * <p>
     * 锁代码块语法一：
     * 代码：synchronized (this) { }
     * 当前对象锁（this = OrderService）
     * OrderService是单例的，通过Spring注解@Service实例化
     * <p>
     * 锁代码块语法二：
     * 代码：
     * private final Object object = new Object();
     * synchronized (object) { }
     * 对象锁（Object）
     * 因为OrderService是单例的，所以作用和写法一相等
     * <p>
     * 锁代码块语法三：
     * 代码：synchronized (OrderService.class) { }
     * 类锁（OrderService.class）
     * 因为OrderService是单例的，所以作用和写法一相等
     * <p>
     * 区别：
     * 对象锁：对象是又类实例化new出来的，当你多个对象时，OrderService有可能不是单例，这个时候多并发可能会获取多个对象，导致锁失效
     * 类锁：类只有一个，就算实例化new多个类，也只有相同的一个类
     */
    Integer createOrderBySynchronizedBlock() throws Exception;

    /**
     * 通过数据库Update语句计算商品库存，
     * 通过Update行锁、悲观锁-并发包下的Lock（ReentrantLock）手动锁代码块、
     * 平台事务管理器-PlatformTransactionManager，手动控制事务 解决并发
     */
    Integer createOrderByReentrantLock() throws Exception;

}
