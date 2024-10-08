/**
 * 项目名称:  rocketmq-plus-spring-boot-starter
 * 公司名称:  YiShoTech
 * All rights Reserved, Designed By YiShoTech 2023-2024
 */
package cn.yishotech.starter.rocketmq.config;

import cn.yishotech.starter.rocketmq.service.IEventPublisher;
import cn.yishotech.starter.rocketmq.service.impl.RocketmqEventPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>类路径:cn.yishotech.starter.rocketmq.config.RocketmqPlusAutoConfiguration</p>
 * <p>类描述:RocketmqPlus自动配置</p>
 * <p>创建人:jason zong</p>
 * <p>创建时间:2024/10/08 19:19</p>
 */
@Configuration
public class RocketmqPlusAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "rocketmq", value = {"name-server"}, matchIfMissing = true)
    public IEventPublisher eventPublisher() {
        return new RocketmqEventPublisher();
    }
}
