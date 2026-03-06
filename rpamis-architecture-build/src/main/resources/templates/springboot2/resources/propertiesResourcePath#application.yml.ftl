# server配置
server:
  # 优雅停机
  shutdown: GRACEFUL
  # 端口及请求基础路径
  port: 8080
  servlet:
    context-path: /${project.artifactId!}
  tomcat:
    # 最大连接数
    max-connections: 15000
    # 请求队列长度
    accept-count: 1000
    uri-encoding: UTF-8
    accesslog:
      # 日志开关
      enabled: true
      # 启用访问日志
      rotate: true
      # 放在日志文件名中的日期格式
      file-date-format: .yyyy-MM-dd
    # 日志输出目录
    basedir: /data/logs/access/

# spring配置
spring:
  application:
    name: ${project.artifactId!}
<#if dependency??>
  <#if dependency.database.enabled==true>
  # 多数据源配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://${dependency.database.host!"localhost"}:${dependency.database.port!"3306"}/${dependency.database.databaseName!"db"}?characterEncoding=utf8&rewriteBatchedStatements=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai
    username: ${dependency.database.userName!"root"}
    password: ${dependency.database.passWord!"root"} 
    druid:
      filter:
        # 日志监控，使用slf4j进行输出
        slf4j:
          enabled: true
          statement-log-error-enabled: true
          statement-create-after-log-enabled: false
          statement-close-after-log-enabled: false
          result-set-open-after-log-enabled: false
          result-set-close-after-log-enabled: false
      # web监控配置
      web-stat-filter:
        enabled: true
        url-pattern: /*
        exclusions: "/druid/*,/ping.*"
        session-stat-enable: true
        session-stat-max-count: 1000
      # 监控页面配置
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/*
        # 允许访问地址
        allow: localhost
        # 不允许清空统计数据
        reset-enable: false
    dynamic:
      # druid全局配置
      druid:
        initial-size: 5
        max-active: 100
        max-wait: 50000
        min-idle: 3
        test-on-borrow: true
        test-while-idle: true
        filters: stat,wall,log4j2
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 300000
        # 自动回收连接
        remove-abandoned: true
        remove-abandoned-timeout-millis: 300000
        # 没有被回收的链接打印堆栈
        log-abandoned: true
        validationQuery: SELECT 1 FROM DUAL
        keep-alive: true
        # 合并集群多个数据源
        use-global-data-source-stat: true
        stat:
          enabled: true
          db-type: mysql
          merge-sql: true
          # 开启慢SQL监控
          log-slow-sql: true
          # 慢SQL阈值2s
          slow-sql-millis: 2000
  </#if>
</#if>
<#if dependency??>
  <#if dependency.redis.enabled==true>
  # redis配置
  redis:
    connect-timeout: 10000
  </#if>
</#if>
  # jackson配置
  jackson:
    time-zone: GMT+8
    date-format: yyyy-MM-dd HH:mm:ss
  # mvc配置
  mvc:
    format:
      date: yyyy-MM-dd HH:mm:ss
    # 开启请求路径匹配，比如请求url为test则，test.jhtm,test.html均会转发到test路由
    # 配合拦截器实现只处理jhtm请求
    pathmatch:
      matching-strategy: ant_path_matcher
      use-suffix-pattern: true

# dubbo多注册中心配置
dubbo:
<#if dependency??>
  <#if dependency.exception.enabled==true>
  provider:
    filter: DubboExceptionFilter
  </#if>
</#if>
  protocol:
    port: -1
    name: dubbo
<#if dependency??>
  <#if dependency.database.enabled==true>
# mybatis-plus配置
mybatis-plus:
  mapper-locations: classpath:/mapper/**.xml
  configuration:
    # 只在控制台打印sql，不输出到日志中
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    # 驼峰和下划线互转
    map-underscore-to-camel-case: false
  global-config:
    db-config:
      # 全局删除字段名
      logic-delete-field: deleted
      # 逻辑已删除值
      logic-delete-value: 1
      # 逻辑未删除值
      logic-not-delete-value: 0
  # 设置domain可用别名
  type-aliases-package: ${project.packageName!}.domain
  </#if>
</#if>

# 日志配置
logging:
  level:
    root: info
    org.springframework: info

<#if dependency??>
  <#if dependency.exception.enabled==true || dependency.security.enabled==true || dependency.healthCheck.enabled==true>
rpamis:
  </#if>
  <#if dependency.exception.enabled==true>
  # rpamis-exception配置
  exception:
    enable: true
    all-exception: true
    rpc-pack: true
  </#if>
  <#if dependency.security.enabled==true>
  # rpamis-security配置
  security:
    # 是否开启安全组件，落库加密，出库脱密，如果不指定加密算法，则默认返回原值
    # 当此开关为false时，无论脱敏切面是否开启，均不生效
    enable: true
    # 加密算法类型，目前只支持sm4
    algorithm: sm4
    # 加密算法密钥，需要自己生成，满足16位即可
    sm4key: 1234567891234567
    # 忽略解密失败，如果解密失败则返回原值，否则抛出异常，如果不填写默认true
    ignore-decrypt-failed: true
    # 是否开启脱敏切面
    desensitization-enable: true
  </#if>
<#if dependency.healthCheck.enabled==true>
  # rpamis-healthcheck配置
  healthcheck:
    enable: true
    liveness-path: /ping
</#if>
</#if>