package com.nttdatates.presentation.presenter;

import com.nttdatates.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementPresenter {

    private UUID movementId;
    private MovementType movementType;
    private Date date;
    private BigDecimal amount;
    private BigDecimal balance;
    private UUID bankAccountId;

}
