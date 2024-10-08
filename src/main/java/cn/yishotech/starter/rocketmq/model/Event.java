/**
 * 项目名称:  rocketmq-spring-boot-starter
 * 公司名称:  YiShoTech
 * All rights Reserved, Designed By YiShoTech 2023-2024
 */
package cn.yishotech.starter.rocketmq.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>类路径:cn.yishotech.starter.model.Event</p>
 * <p>类描述:基础事件实体</p>
 * <p>创建人:jason zong</p>
 * <p>创建时间:2024/10/07 16:12</p>
 */
@Data
public class Event<T> {
    /**
     * 事件ID
     */
    private String id = UUID.randomUUID().toString();
    /**
     * 事件描述
     */
    private String desc;
    /**
     * 事件数据
     */
    private T data;
    /**
     * 发送时间
     */
    private LocalDateTime time = LocalDateTime.now();
}
