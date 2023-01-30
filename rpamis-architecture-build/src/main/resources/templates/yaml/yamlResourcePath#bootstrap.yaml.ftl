spring:
  application:
    name: ${project.artifactId!}
  cloud:
    consul:
      discovery:
        healthCheckPath: ${r"${spring.application.name}"}/health
        healthCheckInterval: 15s
        instance-id: ${r"${spring.application.name}"}:${r"${spring.cloud.client.ip-address}"}:${r"${server.port}"}
        serviceName: ${r"${spring.application.name}"}
      host: 127.0.0.1
      port: 8500
      config:
        enabled: true
        format: yaml
        acl-token: ""
        data-key: "property"
        watch:
          delay: 1000
          enabled: true
