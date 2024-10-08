/**
 * 项目名称:  rocketmq-plus-spring-boot-starter
 * 公司名称:  YiShoTech
 * All rights Reserved, Designed By YiShoTech 2023-2024
 */
package cn.yishotech.starter.rocketmq.service.impl;

import cn.yishotech.starter.rocketmq.model.Event;
import cn.yishotech.starter.rocketmq.service.IEventPublisher;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import static org.apache.rocketmq.client.producer.SendStatus.SEND_OK;

/**
 * <p>类路径:cn.yishotech.starter.rocketmq.service.impl.RocketmqEventPublisher</p>
 * <p>类描述:Rocketmq事件发布实现</p>
 * <p>创建人:jason zong</p>
 * <p>创建时间:2024/10/08 19:17</p>
 */
@Slf4j
@Component
public class RocketmqEventPublisher implements IEventPublisher {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public <T extends Event<?>> void publish(String topic, T event) {
        Message<T> message = MessageBuilder.withPayload(event).setHeader(RocketMQHeaders.KEYS, event.getId()).build();
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        if (SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("同步消息发送成功,topic:{} message:{}", topic, JSON.toJSONString(event));
        } else {
            log.info("同步消息发送失败,topic:{} message:{}", topic, JSON.toJSONString(event));
        }
    }

    @Override
    public <T extends Event<?>> void publish(String topic, String tag, T event) {
        topic = buildDestination(topic, tag);
        publish(topic, event);
    }

    @Override
    public <T extends Event<?>> void asyncPublish(String topic, T event) {
        Message<T> message = MessageBuilder.withPayload(event).setHeader(RocketMQHeaders.KEYS, event.getId()).build();
        rocketMQTemplate.asyncSend(topic, message, new SendCallback() {

            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步消息发送成功,topic:{} message:{}", topic, JSON.toJSONString(event));
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("异步消息发送失败,topic:{} message:{} error:{}", topic, JSON.toJSONString(event), throwable.getMessage());
            }
        });
    }

    @Override
    public <T extends Event<?>> void asyncPublish(String topic, String tag, T event) {
        topic = buildDestination(topic, tag);
        asyncPublish(topic, event);
    }

    @Override
    public <T extends Event<?>> void delayPublish(String topic, String tag, T event, int delayLevel) {
        topic = buildDestination(topic, tag);
        delayPublish(topic, event, delayLevel);
    }

    @Override
    public <T extends Event<?>> void delayPublish(String topic, T event, int delayLevel) {
        Message<T> message = MessageBuilder.withPayload(event).setHeader(RocketMQHeaders.KEYS, event.getId()).build();
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message, 3000, delayLevel);
        if (SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("延迟消息发送成功,topic:{} message:{}", topic, JSON.toJSONString(event));
        } else {
            log.info("延迟消息发送失败,topic:{} message:{}", topic, JSON.toJSONString(event));
        }
    }

    @Override
    public <T extends Event<?>> void scheduledPublish(String topic, T event, int time) {
        Message<T> message = MessageBuilder.withPayload(event).setHeader(RocketMQHeaders.KEYS, event.getId()).build();
        // 生成投递时间
        long pushTime = System.currentTimeMillis() + ((long) time * 60 * 1000);
        rocketMQTemplate.syncSendDeliverTimeMills(topic, message, pushTime);
    }

    @Override
    public <T extends Event<?>> void scheduledPublish(String topic, String tag, T event, int time) {
        topic = buildDestination(topic, tag);
        scheduledPublish(topic, event, time);
    }

    @Override
    public <T extends Event<?>> TransactionSendResult transactionSend(String topic, T event) {
        Message<T> message = MessageBuilder.withPayload(event).setHeader(RocketMQHeaders.KEYS, event.getId()).build();
        return rocketMQTemplate.sendMessageInTransaction(topic, message, null);
    }

    @Override
    public <T extends Event<?>> TransactionSendResult transactionSend(String topic, String tag, T event) {
        topic = buildDestination(topic, tag);
        return transactionSend(topic, event);
    }

    @Override
    public <T extends Event<?>> void orderSend(String topic, T event, String hashKey) {
        Message<T> message = MessageBuilder.withPayload(event).setHeader(RocketMQHeaders.KEYS, event.getId()).build();
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(topic, message, hashKey);
        if (SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("顺序消息发送成功,topic:{} message:{}", topic, JSON.toJSONString(event));
        } else {
            log.info("顺序消息发送失败,topic:{} message:{}", topic, JSON.toJSONString(event));
        }
    }

    @Override
    public <T extends Event<?>> void orderSend(String topic, String tag, T event, String hashKey) {
        topic = buildDestination(topic, tag);
        orderSend(topic, event, hashKey);
    }

    /**
     * 构建发送目的地
     *
     * @param topic 主题
     * @param tag   标签
     * @return 目的地
     */
    private String buildDestination(String topic, String tag) {
        return String.format("%s:%s", topic, tag);
    }
}
