package com.nttdatates.presentation.controller;

import com.nttdatates.presentation.presenter.BankAccountPresenter;
import com.nttdatates.service.BankAccountService;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Generated
@RestController
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/getBankAccountById")
    public BankAccountPresenter getBankAccountById(@RequestParam("bankAccountId") UUID bankAccountId){
        return bankAccountService.toPresenter(bankAccountService.getBankAccountById(bankAccountId));
    }

    @GetMapping("/getBankAccountByNumber")
    public BankAccountPresenter getBankAccountByNumber(@RequestParam("bankAccountNumber") String bankAccountNumber){
        return bankAccountService.getBankAccountByNumber(bankAccountNumber);
    }

    @PostMapping("/postBankAccount")
    BankAccountPresenter saveBankAccount(@RequestBody BankAccountPresenter bankAccountPresenter){
        return bankAccountService.saveBankAccount(bankAccountPresenter);
    }

    @PutMapping("/putBankAccount")
    BankAccountPresenter updateBankAccount(@RequestBody BankAccountPresenter bankAccountPresenter){
        return bankAccountService.updateBankAccount(bankAccountPresenter);
    }

    @DeleteMapping("/deleteBankAccountById")
    public void deleteBankAccountById(@RequestParam("bankAccountId") UUID bankAccountId){
        bankAccountService.deleteBankAccountById(bankAccountId);
    }

}
