package com.mall.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能描述：秒杀队列发送者
 *
 * @author yhc
 */
@Service
@Slf4j
public class MQSender {
    private final String SECKILL_QUEUE = "SECKILL_QUEUE";
    private final String SECKILL_CHANGE = "SECKILL_CHANGE";
    private final String SECKILL_ROUTING = "yhc.seckill.order";

    @Resource
    private RabbitTemplate rabbitTemplate;

    public boolean senderNormal(String message) {
        rabbitTemplate.convertAndSend(SECKILL_CHANGE,SECKILL_ROUTING,message);
        log.info("发送消息："+message);
        return true;
    }

    public boolean senderSecKill(String message) {
        rabbitTemplate.convertAndSend(SECKILL_CHANGE,SECKILL_ROUTING,message);
        log.info("发送消息："+message);
        return true;
    }
}
