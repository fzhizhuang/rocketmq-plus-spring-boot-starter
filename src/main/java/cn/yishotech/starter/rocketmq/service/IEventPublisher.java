/**
 * 项目名称:  rocketmq-plus-spring-boot-starter
 * 公司名称:  YiShoTech
 * All rights Reserved, Designed By YiShoTech 2023-2024
 */
package cn.yishotech.starter.rocketmq.service;

import cn.yishotech.starter.rocketmq.model.Event;
import org.apache.rocketmq.client.producer.TransactionSendResult;

/**
 * <p>类路径:cn.yishotech.starter.rocketmq.service.IEventPublisher</p>
 * <p>类描述:MQ事件发布服务接口</p>
 * <p>创建人:jason zong</p>
 * <p>创建时间:2024/10/08 19:15</p>
 */
public interface IEventPublisher {

    /**
     * 发送同步消息
     *
     * @param topic 主题
     * @param event 事件
     * @param <T>   泛型
     */
    <T extends Event<?>> void publish(String topic, T event);

    /**
     * 发送同步消息
     *
     * @param topic 主题
     * @param tag   标签
     * @param event 事件
     * @param <T>   泛型
     */
    <T extends Event<?>> void publish(String topic, String tag, T event);

    /**
     * 发送异步消息
     *
     * @param topic 主题
     * @param event 事件
     * @param <T>   泛型
     */
    <T extends Event<?>> void asyncPublish(String topic, T event);

    /**
     * 发送异步消息
     *
     * @param topic 主题
     * @param tag   标签
     * @param event 事件
     * @param <T>   泛型
     */
    <T extends Event<?>> void asyncPublish(String topic, String tag, T event);

    /**
     * 发送延迟消息
     *
     * @param topic      主题
     * @param tag        标签
     * @param event      事件
     * @param delayLevel 延迟等级
     * @param <T>        泛型
     */
    <T extends Event<?>> void delayPublish(String topic, String tag, T event, int delayLevel);

    /**
     * 发送延迟消息
     *
     * @param topic      主题
     * @param event      事件
     * @param delayLevel 延迟等级
     * @param <T>        泛型
     */
    <T extends Event<?>> void delayPublish(String topic, T event, int delayLevel);

    /**
     * 定时投递消息
     *
     * @param topic 主题
     * @param event 事件
     * @param time  消息投递时间,单位分钟
     * @param <T>   泛型
     */
    <T extends Event<?>> void scheduledPublish(String topic, T event, int time);


    /**
     * 定时投递消息
     *
     * @param topic 主题
     * @param tag   标签
     * @param event 事件
     * @param time  消息投递时间,单位分钟
     * @param <T>   泛型
     */
    <T extends Event<?>> void scheduledPublish(String topic, String tag, T event, int time);

    /**
     * 发送事务消息
     *
     * @param topic 主题
     * @param event 事件
     * @param <T>   泛型
     * @return 事务消息结果
     */
    <T extends Event<?>> TransactionSendResult transactionSend(String topic, T event);

    /**
     * 发送事务消息
     *
     * @param topic 主题
     * @param tag   标签
     * @param event 事件
     * @param <T>   泛型
     * @return 事务消息结果
     */
    <T extends Event<?>> TransactionSendResult transactionSend(String topic, String tag, T event);

    /**
     * 发送顺序消息
     *
     * @param topic   主题
     * @param event   事件
     * @param hashKey hashKey
     * @param <T>     泛型
     */
    <T extends Event<?>> void orderSend(String topic, T event, String hashKey);

    /**
     * 发送顺序消息
     *
     * @param topic   主题
     * @param tag     标签
     * @param event   事件
     * @param hashKey hashKey
     * @param <T>     泛型
     */
    <T extends Event<?>> void orderSend(String topic, String tag, T event, String hashKey);

}
