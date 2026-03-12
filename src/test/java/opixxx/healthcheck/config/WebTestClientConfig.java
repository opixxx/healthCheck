package opixxx.healthcheck.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;

@TestConfiguration
public class WebTestClientConfig {

	@Bean
	public WebTestClient webTestClient(@Value("${local.server.port}") int port) {
		return WebTestClient.bindToServer()
			.baseUrl("http://localhost:" + port)
			.build();
	}
}