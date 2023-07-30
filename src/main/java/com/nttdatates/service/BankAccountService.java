package com.nttdatates.service;

import com.nttdatates.entity.BankAccount;
import com.nttdatates.presentation.presenter.BankAccountPresenter;

import java.util.UUID;

public interface BankAccountService {

    BankAccount getBankAccountById(UUID bankAccountId);

    BankAccountPresenter getBankAccountByNumber(String bankAccountNumber);

    BankAccountPresenter saveBankAccount(BankAccountPresenter bankAccountPresenter);

    BankAccountPresenter updateBankAccount(BankAccountPresenter bankAccountPresenter);

    void deleteBankAccountById(UUID bankAccountId);

    BankAccountPresenter toPresenter(BankAccount bankAccount);

}
