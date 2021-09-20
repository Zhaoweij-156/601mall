package com.mall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述：rabbitmq topic配置类
 *
 * @author yhc
 */

@Configuration
public class RabbitMQTopicConfig {
    private final String SECKILL_QUEUE = "SECKILL_QUEUE";
    private final String SECKILL_CHANGE = "SECKILL_CHANGE";
    /**
     * 秒杀专属队列
     */
    private final String SECKILL_ROUTING = "yhc.seckill.#";

    @Bean
    public Queue queue(){
        return new Queue(SECKILL_QUEUE,false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(SECKILL_CHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(SECKILL_ROUTING);
    }
}