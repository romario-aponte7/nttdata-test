package com.nttdatates.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "clients")
@AllArgsConstructor

public class Client extends Person {


    private String password;

    private Boolean active;

    @OneToMany(mappedBy = "client")
    private Collection<BankAccount> bankAccounts;

}
