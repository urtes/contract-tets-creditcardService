package com.retailbank.creditcardService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureStubRunner(ids = "com.retailbank:creditcheckService:+:stubs:8080", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class CreditCardServiceApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldGrantApplicationWhenCreditScoreIsHigh() throws Exception {
		mockMvc.perform(
				post("/credit-card-application")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{"+
							"\"citizenNumber\": 1234," +
							"\"cardType\": \"GOLD\"" +
							"}"
					))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
						.json("{"+
								"\"status\": \"GRANTED\"" +
								"}"))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

}
