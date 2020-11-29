package com.retailbank.creditcardService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditCardResponse {

    Status status;

    public enum Status {
        GRANTED
    }
}
