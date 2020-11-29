package com.retailbank.creditcardService.controller;

import com.retailbank.creditcardService.domain.CreditCardRequest;
import com.retailbank.creditcardService.domain.CreditCardResponse;
import com.retailbank.creditcardService.domain.CreditCheckRequest;
import com.retailbank.creditcardService.domain.CreditCheckResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class CreditCardApplicationController {

    private final RestTemplate restTemplate;
    private final String creditCheckServiceBaseUrl;

    public CreditCardApplicationController(RestTemplate restTemplate,
                                           @Value("${creditcheckService.baseUrl}") String creditCheckServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.creditCheckServiceBaseUrl = creditCheckServiceBaseUrl;
    }

    @PostMapping("/credit-card-application")
    public CreditCardResponse applyForCreditCard(@RequestBody CreditCardRequest creditCardRequest) {
        int citizenNumber = creditCardRequest.getCitizenNumber();

        final String uri = UriComponentsBuilder.fromHttpUrl(creditCheckServiceBaseUrl)
                .pathSegment("credit-scores")
                .toUriString();

        CreditCheckResponse creditCheckResponse = restTemplate.postForObject(
                uri,
                new CreditCheckRequest(citizenNumber),
                CreditCheckResponse.class);

        if (creditCardRequest.getCardType() == CreditCardRequest.CardType.GOLD
                && creditCheckResponse.getScore() == CreditCheckResponse.Score.HIGH) {
            return new CreditCardResponse(CreditCardResponse.Status.GRANTED);
        }

        throw new RuntimeException("Card and score not yet implemented");
    }

}
