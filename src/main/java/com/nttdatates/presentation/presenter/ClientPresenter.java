package com.nttdatates.presentation.presenter;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ClientPresenter extends PersonPresenter {

    private String password;
    private Boolean active;
    private Set<BankAccountPresenter> bankAccountPresenters;
}
