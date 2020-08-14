package org.xmin.demo.boot.amqp.aop;

import com.minlc.spring.demo.core.annotation.OnMessage;
import com.minlc.spring.demo.core.annotation.RetryMessage;
import com.rabbitmq.client.Channel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.Message;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-08-11
 **/
@Aspect
@Order
@Component
public class AutoAckMessageHandler {

    @Pointcut("@annotation(org.xmin.demo.core.annotation.OnMessage) || @annotation(org.xmin.demo.core.annotation.RetryMessage)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {

        Signature signature = pjp.getSignature();
        MethodSignature ms = (MethodSignature) signature;
        Method method = ms.getMethod();
        final OnMessage onMessage = method.getAnnotation(OnMessage.class);
        final RetryMessage retryMessage = method.getAnnotation(RetryMessage.class);

        if ((onMessage == null || !onMessage.enabled())) {
            return pjp.proceed();
        }
        if ((retryMessage == null || retryMessage.enabled())) {
            return pjp.proceed();
        }

        Object[] args = pjp.getArgs();
        Message message = (Message) args[1];
        Channel channel = (Channel) args[2];
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        Object proceed = null;
        try {
            proceed = pjp.proceed();
            // 确认消息
            channel.basicAck(deliveryTag, false);
            return proceed;
        } catch (Throwable e) {
            if (onMessage != null && onMessage.enabled()) {
                // 拒绝并进重试队列
                channel.basicNack(deliveryTag, false, false);
                return proceed;
            } else if (retryMessage != null && retryMessage.enabled()) {
                // 重试5次后依然失败进死信队列
//                throw new BusinessException(ResultCode.FAIL.custom("message retry fail!"), e);
                throw new RuntimeException("message retry fail!");
            }
        }

        return pjp.proceed();
    }
}
