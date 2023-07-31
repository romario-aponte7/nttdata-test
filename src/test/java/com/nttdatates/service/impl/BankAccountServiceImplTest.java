package com.nttdatates.service.impl;

import com.nttdatates.entity.BankAccount;
import com.nttdatates.repository.BankAccountRepository;
import com.nttdatates.repository.ClientRepository;
import com.nttdatates.service.MovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private MovementService movementService;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBankAccountById_ValidId_ReturnsBankAccount() {
        UUID bankAccountId = UUID.randomUUID();
        BankAccount mockBankAccount = new BankAccount();
        when(bankAccountRepository.findById(bankAccountId)).thenReturn(Optional.of(mockBankAccount));

        BankAccount result = bankAccountService.getBankAccountById(bankAccountId);

        assertNotNull(result);
        assertEquals(mockBankAccount, result);
    }

}
