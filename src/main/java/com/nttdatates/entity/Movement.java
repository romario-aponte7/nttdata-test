package com.nttdatates.entity;

import com.nttdatates.enums.MovementType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor(force = true)
@Table(name = "movements")
@EqualsAndHashCode(of = "movementId")
@ToString(of = "movementId")
@Builder
@AllArgsConstructor
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID movementId;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    private Date date;

    private BigDecimal amount;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    @NotNull
    private BankAccount bankAccount;
}
