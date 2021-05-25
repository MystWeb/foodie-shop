package cn.myst.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author ziming.xing
 * Create Date：2021/5/23
 */
@Slf4j
@Aspect
@Component
public class ServiceLogAspect {

    /**
     * AOP通知：
     * 1、前置通知：在方法调用之前执行
     * 2、后置通知：在方法调用之后执行
     * 3、环绕通知：在方法调用之前和之后，都分别可以执行的通知
     * 4、异常通知：如果在方法调用过程中发生异常，则通知
     * 5、最终通知：在方法调用之后执行
     * <p>
     * 切面表达式：
     * execution：代表所有要执行的表达式主体
     * 第一处： * 代表方法返回类型， * 代表所有类型
     * 第二处： 包名代表aop监控的类所在的包
     * 第三处： .. 代表该包以及其子包下的所有类方法
     * 第四处： * 代表类名，* 代表所有类
     * 第五处： *(..) * 代表类中的方法名
     */
    @Around("execution(* cn.myst.web.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取调用方法后的类名与方法名
        log.info("======= 开始执行 {}.{} =======",
                // 目标是哪个类
                joinPoint.getTarget().getClass(),
                // 签名
                joinPoint.getSignature().getName());

        // 记录开始时间
        LocalDateTime begin = LocalDateTime.now();
        // 执行目标 Service
        Object result = joinPoint.proceed();
        // 记录结束时间
        LocalDateTime end = LocalDateTime.now();
        // 获取时间差
        Duration duration = java.time.Duration.between(begin, end);
        long takeTime = duration.toMillis();
        if (takeTime > 3000) {
            log.error("======= 执行结束，耗时：{} 毫秒 =======", takeTime);
        } else {
            log.info("======= 执行结束，耗时：{} 毫秒 =======", takeTime);
        }
        return result;
    }

}
