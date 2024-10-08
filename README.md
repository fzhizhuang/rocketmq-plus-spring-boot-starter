<h3 align="center">RocketMQ Starter</h3>

## 介绍
Rocketmq组件,封装rocketmq消息发送服务

### 使用

#### pom引入依赖

```xml
<dependency>
    <groupId>cn.yishotech</groupId>
    <artifactId>rocketmq-plus-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```
### 在application.yml中配置
```yaml
rocketmq:
  name-server: 127.0.0.1:9876
  producer:
    group: user_group
    send-message-timeout: 3000
    max-message-size: 4096
    retry-times-when-send-failed: 3
    retry-times-when-send-async-failed: 2
  consumer:
    group: user_group
    pull-batch-size: 10
```
## 许可证

根据 License 许可证分发。打开 [LICENSE](LICENSE) 查看更多内容。
