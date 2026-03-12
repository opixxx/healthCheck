package opixxx.healthcheck;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import opixxx.healthcheck.config.TestContainersConfig;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = TestContainersConfig.class)
class HealthCheckApplicationTests {

	@Test
	void contextLoads() {
	}
}