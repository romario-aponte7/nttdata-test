package com.nttdatates.presentation.controller;

import com.nttdatates.entity.BankAccount;
import com.nttdatates.entity.Client;
import com.nttdatates.enums.BankAccountStatus;
import com.nttdatates.enums.BankAccountType;
import com.nttdatates.presentation.presenter.BankAccountPresenter;
import com.nttdatates.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class BankAccountControllerTest {

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountController bankAccountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetBankAccountByNumber() {
        String bankAccountNumber = "1234567890";
        BankAccountPresenter mockBankAccountPresenter = new BankAccountPresenter(); // Set necessary fields

        when(bankAccountService.getBankAccountByNumber(bankAccountNumber)).thenReturn(mockBankAccountPresenter);

        BankAccountPresenter result = bankAccountController.getBankAccountByNumber(bankAccountNumber);

        assertEquals(mockBankAccountPresenter, result);
    }

    @Test
    void testSaveBankAccount() {
        BankAccountPresenter bankAccountPresenter = new BankAccountPresenter(); // Set necessary fields
        BankAccountPresenter mockSavedBankAccountPresenter = new BankAccountPresenter(); // Set necessary fields

        when(bankAccountService.saveBankAccount(bankAccountPresenter)).thenReturn(mockSavedBankAccountPresenter);

        BankAccountPresenter result = bankAccountController.saveBankAccount(bankAccountPresenter);

        assertEquals(mockSavedBankAccountPresenter, result);
    }

    @Test
    void testUpdateBankAccount() {
        BankAccountPresenter bankAccountPresenter = new BankAccountPresenter(); // Set necessary fields
        BankAccountPresenter mockUpdatedBankAccountPresenter = new BankAccountPresenter(); // Set necessary fields

        when(bankAccountService.updateBankAccount(bankAccountPresenter)).thenReturn(mockUpdatedBankAccountPresenter);

        BankAccountPresenter result = bankAccountController.updateBankAccount(bankAccountPresenter);

        assertEquals(mockUpdatedBankAccountPresenter, result);
    }

    @Test
    void testDeleteBankAccountById() {
        UUID bankAccountId = UUID.randomUUID();

        // For this test case, we don't need to return anything from the service method.
        // We will just verify that the service method is called with the correct argument.

        bankAccountController.deleteBankAccountById(bankAccountId);

        verify(bankAccountService, times(1)).deleteBankAccountById(bankAccountId);
    }
}
