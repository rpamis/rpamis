package ${project.packageName!}.common.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * MongoDb配置
 */
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoConfig.class);

    @Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private SocketSettings socketSettings;

    @Autowired
    private ConnectionPoolSettings connectionPoolSettings;

    @Autowired
    private ServerSettings serverSettings;

    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    private List<ServerAddress> genServerAddressList() {
        // MongoDB地址列表
        List<ServerAddress> serverAddresses = new ArrayList<>();
        for (String source : mongoProperties.getHost().split(",")) {
            String[] hostAndPort = source.split(":");
            try {
                InetAddress hostAddress = InetAddress.getByName(hostAndPort[0]);
                Integer port = hostAndPort.length == 1 ? null : Integer.parseInt(hostAndPort[1]);

                serverAddresses.add(port == null ? new ServerAddress(hostAddress) : new ServerAddress(hostAddress, port));
            } catch (UnknownHostException e) {
                LOGGER.warn("Could not parse address {} '{}'. Check your replica set configuration!", "host", hostAndPort[0]);
            } catch (NumberFormatException e) {
                LOGGER.warn("Could not parse address {} '{}'. Check your replica set configuration!", "port", hostAndPort[1]);
            }
        }

        if (serverAddresses.isEmpty()) {
            throw new IllegalArgumentException(
                    "Could not resolve at least one server of the replica set configuration! Validate your config!");
        }
        return serverAddresses;
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {

        if (StringUtils.isNotEmpty(mongoProperties.getUsername())) {
            builder.credential(MongoCredential.createScramSha1Credential(mongoProperties.getUsername(), mongoProperties.getAuthenticationDatabase() == null ? this.mongoProperties.getDatabase() : mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword() == null ? null : mongoProperties.getPassword()));
        }

        builder.applyToClusterSettings(settings ->
                settings.hosts(this.genServerAddressList())
                        .requiredReplicaSetName(mongoProperties.getReplicaSetName())
        ).applyToSocketSettings(settings ->
                settings.connectTimeout(socketSettings.getConnectTimeout(), TimeUnit.MILLISECONDS)
                        .readTimeout(socketSettings.getReadTimeout(), TimeUnit.MILLISECONDS)
        ).applyToConnectionPoolSettings(settings ->
                settings.minSize(connectionPoolSettings.getMinSize())
                        .maxSize(connectionPoolSettings.getMaxSize())
                        .maxWaitTime(connectionPoolSettings.getMaxWaitTime(), TimeUnit.MILLISECONDS)
                        .maintenanceFrequency(connectionPoolSettings.getMaintenanceFrequency(), TimeUnit.MILLISECONDS)
                        .maintenanceInitialDelay(connectionPoolSettings.getMaintenanceInitialDelay(), TimeUnit.MILLISECONDS)
                        .maxConnecting(connectionPoolSettings.getMaxConnecting())
                        .maxConnectionIdleTime(connectionPoolSettings.getMaxConnectionIdleTime(), TimeUnit.MILLISECONDS)
                        .maxConnectionLifeTime(connectionPoolSettings.getMaxConnectionLifeTime(), TimeUnit.MILLISECONDS)

        ).applyToServerSettings(settings ->
                settings.heartbeatFrequency(serverSettings.getHeartbeatFrequency(), TimeUnit.MILLISECONDS)
                        .minHeartbeatFrequency(serverSettings.getMinHeartbeatFrequency(), TimeUnit.MILLISECONDS)
        );
    }
}
