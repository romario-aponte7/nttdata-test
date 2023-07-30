package com.nttdatates.presentation.presenter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementsReportPresenter {
    private String date;
    private String client;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private String status;
    private BigDecimal movementAmount;
    private BigDecimal availableBalance;

}
