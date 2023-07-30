package com.nttdatates.entity;

import com.nttdatates.enums.BankAccountStatus;
import com.nttdatates.enums.BankAccountType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor(force = true)
@Table(name = "bankAccounts")
@EqualsAndHashCode(of = "bankAccountId")
@ToString(of = "bankAccountId")
@Builder
@AllArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bankAccountId;

    @NotNull
    @Column(unique = true)
    private String number;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BankAccountType type;

    private BigDecimal initialBalance;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BankAccountStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull
    private Client client;

    @OneToMany(mappedBy = "bankAccount")
    private Collection<Movement> movements;
}
