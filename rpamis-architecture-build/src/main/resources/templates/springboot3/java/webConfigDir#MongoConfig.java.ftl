package ${project.packageName!}.common.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * MongoDb配置
 */
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Resource
    private MongoProperties mongoProperties;

    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    /**
     * 配置MongoClient带有mongo工厂设置
     * 用于mongoTemplate配置
     *
     * @return MongoClient
     */
    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://" + mongoProperties.getHost());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                // 设置Factory参数，默认keepAlive true
                .applyToSocketSettings(builder -> {
                    builder.connectTimeout(5000, TimeUnit.MILLISECONDS);
                    builder.readTimeout(1500, TimeUnit.MILLISECONDS);
                })
                .applyToConnectionPoolSettings(builder -> {
                    // 最小连接数
                    builder.minSize(10);
                    // 最大连接数
                    builder.maxSize(500);
                    // 最长等待时间
                    builder.maxWaitTime(1500, TimeUnit.MILLISECONDS);
                })
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    /**
     * 创建MongoTemplate
     *
     * @return MongoTemplate
     */
    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), getDatabaseName());
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoMapping.setCustomConversions(addConverter());
        mongoMapping.afterPropertiesSet();
        return mongoTemplate;
    }
}
