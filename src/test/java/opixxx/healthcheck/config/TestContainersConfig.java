package opixxx.healthcheck.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

public class TestContainersConfig
	implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
		.withDatabaseName("healthcheck")
		.withUsername("root")
		.withPassword("1234")
		.withReuse(true);

	public static final GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
		.withExposedPorts(6379)
		.withReuse(true);

	public static final GenericContainer<?> kafka = new GenericContainer<>(
		DockerImageName.parse("apache/kafka:3.7.0"))
		.withExposedPorts(9092)
		.withEnv("KAFKA_NODE_ID", "1")
		.withEnv("KAFKA_PROCESS_ROLES", "broker,controller")
		.withEnv("KAFKA_LISTENERS", "PLAINTEXT://:9092,CONTROLLER://:9093")
		.withEnv("KAFKA_CONTROLLER_LISTENER_NAMES", "CONTROLLER")
		.withEnv("KAFKA_LISTENER_SECURITY_PROTOCOL_MAP", "CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT")
		.withEnv("KAFKA_CONTROLLER_QUORUM_VOTERS", "1@localhost:9093")
		.withEnv("KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR", "1")
		.withEnv("KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR", "1")
		.withEnv("KAFKA_TRANSACTION_STATE_LOG_MIN_ISR", "1")
		.withEnv("KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS", "0")
		.withReuse(true);

	static {
		Startables.deepStart(mysql, redis, kafka).join();
	}

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		TestPropertyValues.of(
			"spring.datasource.url=" + mysql.getJdbcUrl(),
			"spring.datasource.username=" + mysql.getUsername(),
			"spring.datasource.password=" + mysql.getPassword(),
			"spring.data.redis.host=" + redis.getHost(),
			"spring.data.redis.port=" + redis.getMappedPort(6379),
			"spring.kafka.bootstrap-servers=" + kafka.getHost() + ":" + kafka.getMappedPort(9092)
		).applyTo(context);
	}
}