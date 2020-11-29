package com.retailbank.creditcardService.domain;

import lombok.Data;

@Data
public class CreditCardRequest {

    private int citizenNumber;
    private CardType cardType;

    public enum CardType {
        GOLD
    }
}
