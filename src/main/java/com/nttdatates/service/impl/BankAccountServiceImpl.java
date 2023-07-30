package com.nttdatates.service.impl;

import com.nttdatates.entity.BankAccount;
import com.nttdatates.entity.Client;
import com.nttdatates.exception.ValidationException;
import com.nttdatates.presentation.presenter.BankAccountPresenter;
import com.nttdatates.presentation.presenter.MovementPresenter;
import com.nttdatates.repository.BankAccountRepository;
import com.nttdatates.repository.ClientRepository;
import com.nttdatates.service.BankAccountService;
import com.nttdatates.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MovementService movementService;

    @Override
    public BankAccount getBankAccountById(UUID bankAccountId) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(bankAccountId);
        if (bankAccount.isPresent()) {
            return bankAccount.get();
        } else {
            throw new ValidationException("Bank account not found");
        }
    }

    @Override
    public BankAccountPresenter getBankAccountByNumber(String bankAccountNumber) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findByNumber(bankAccountNumber);
        if (bankAccount.isPresent()) {
            return toPresenter(bankAccount.get());
        } else {
            throw new ValidationException("Bank account not found");
        }
    }

    @Override
    @Transactional
    public BankAccountPresenter saveBankAccount(BankAccountPresenter bankAccountPresenter) {

        String number = bankAccountPresenter.getNumber();
        if (number == null || number.isEmpty() || number.isBlank()) {
            throw new ValidationException("Bank account must have identification number");
        }
        if (bankAccountPresenter.getClientId() == null) {
            throw new ValidationException("Bank account must have clientId");
        }
        Optional<Client> client = clientRepository.findById(bankAccountPresenter.getClientId());
        if (client.isEmpty()) {
            throw new ValidationException("Client not found");
        }
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByNumber(number);
        if (optionalBankAccount.isPresent()) {
            throw new ValidationException("Bank account has already been registered");
        } else {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setNumber(bankAccountPresenter.getNumber());
            bankAccount.setType(bankAccountPresenter.getType());
            bankAccount.setInitialBalance(bankAccountPresenter.getInitialBalance());
            bankAccount.setStatus(bankAccountPresenter.getStatus());
            bankAccount.setClient(client.get());
            BankAccount bankAccountSaved = bankAccountRepository.save(bankAccount);
            return toPresenter(bankAccountSaved);
        }
    }

    @Override
    @Transactional
    public BankAccountPresenter updateBankAccount(BankAccountPresenter bankAccountPresenter) {

        UUID bankAccountId = bankAccountPresenter.getBankAccountId();
        if (bankAccountId == null) {
            throw new ValidationException("bankAccountId must have ID");
        }
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(bankAccountId);
        if (bankAccount.isEmpty()) {
            throw new ValidationException("Client not found");
        }
        Optional<Client> client = clientRepository.findById(bankAccountPresenter.getClientId());
        if (client.isEmpty()) {
            throw new ValidationException("Client not found");
        }
        bankAccount.get().setNumber(bankAccountPresenter.getNumber());
        bankAccount.get().setType(bankAccountPresenter.getType());
        bankAccount.get().setInitialBalance(bankAccountPresenter.getInitialBalance());
        bankAccount.get().setStatus(bankAccountPresenter.getStatus());
        bankAccount.get().setClient(client.get());
        BankAccount bankAccountSaved = bankAccountRepository.save(bankAccount.get());
        return toPresenter(bankAccountSaved);

    }

    @Override
    @Transactional
    public void deleteBankAccountById(UUID bankAccountId) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(bankAccountId);
        if (bankAccount.isPresent()) {
            if (bankAccount.get().getMovements() != null && !bankAccount.get().getMovements().isEmpty()) {
                bankAccountRepository.deleteById(bankAccount.get().getBankAccountId());
            }else {
                throw new ValidationException("The bank account has movement");
            }

        } else {
            throw new ValidationException("Bank account not found");
        }
    }

    @Override
    public BankAccountPresenter toPresenter(BankAccount bankAccount) {
        Set<MovementPresenter> movementPresenters = new HashSet<>();
        if (bankAccount.getMovements() != null) {
            bankAccount.getMovements().forEach(movement -> {
                movementPresenters.add(movementService.toPresenter(movement));
            });
        }
        return BankAccountPresenter.builder()
                .bankAccountId(bankAccount.getBankAccountId())
                .number(bankAccount.getNumber())
                .type(bankAccount.getType())
                .initialBalance(bankAccount.getInitialBalance())
                .status(bankAccount.getStatus())
                .movementPresenters(movementPresenters)
                .clientId(bankAccount.getClient().getPersonId())
                .build();

    }
}
