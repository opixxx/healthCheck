package opixxx.healthcheck.cucumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.client.RestTestClient;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HealthStepDefinitions {

	@Autowired
	private RestTestClient restTestClient;  // 새로운 클라이언트

	private RestTestClient.ResponseSpec response;

	@When("클라이언트가 GET \\/health API를 요청하면")
	public void requestHealth() {
		response = restTestClient.get()
			.uri("/health")
			.exchange();
	}

	@Then("응답 상태 코드는 {int} 이어야 한다")
	public void checkStatus(int statusCode) {
		response.expectStatus().isEqualTo(statusCode);
	}

	@And("응답 본문은 {string} 이어야 한다")
	public void checkBody(String body) {
		response.expectBody(String.class).isEqualTo(body);
	}
}