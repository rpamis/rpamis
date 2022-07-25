
spring:
  application:
    name: reliableMessage
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/reliableMessage?characterEncoding=utf8&rewriteBatchedStatements=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      acks: all
      # 对于能被RetriableException捕获的属于可重试异常，则会进行重试，如果不是可重试的消息，则抛出错误信息
      retries: 3
    consumer:
      group-id: message-test
      # earliest和latest才有效，如果earliest重新0开始读取，如果是latest从logfile的offset读取。一般情况下我们都是设置earliest
      auto-offset-reset: earliest
      enable-auto-commit: false
      # 如果'enable.auto.commit'为true，则消费者偏移自动提交给Kafka的频率（以毫秒为单位），默认值为5000。
      auto-commit-interval: 1000
      max-poll-records: 5
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    # 手动ack支持
    listener:
      # 手动提交
      ack-mode: manual
      # SINGLE或BATCH
      type: SINGLE
      # 设置并发数 并发消费
      # 比如会启动3条线程进行监听，而要监听的topic有5个partition，意味着将有2条线程都是分配到2个partition，还有1条线程分配到1个partition。
      # 注意：并发量根据实际分区数决定，必须小于等于分区数，否则会有线程一直处于空闲状态。
      # 如果并行度设置不正确，将会导致Kafka服务重复rebalance
      # concurrency*server=partition
      concurrency: 3
      # 如果当前消费者拉取到了空数据，则等待100ms再去拉取
      pollTimeout: 100
    properties:
      # 最大拉取间隔，默认5分钟
      max:
        poll:
          interval:
            ms: 500000

logging:
  level:
    org:
      springframework:
        kafka: ERROR # spring-kafka
      apache:
        kafka: ERROR # kafka

server:
  port: 8768
  servlet:
    context-path: /reliableMessage

mybatis-plus:
  mapper-locations: classpath:/mapper/*.xml
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      # 全局删除字段名
      logic-delete-field: deleted
      # 逻辑已删除值
      logic-delete-value: 1
      # 逻辑未删除值
      logic-not-delete-value: 0

message:
  topic: test_message
