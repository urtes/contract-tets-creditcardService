package com.retailbank.creditcardService.domain;

import lombok.Data;

@Data
public class CreditCheckResponse {
    private Score score;

    public enum Score {
        HIGH
    }
}
