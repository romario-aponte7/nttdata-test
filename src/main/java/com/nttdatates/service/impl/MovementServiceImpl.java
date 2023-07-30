package com.nttdatates.service.impl;

import com.nttdatates.entity.BankAccount;
import com.nttdatates.entity.Client;
import com.nttdatates.entity.Movement;
import com.nttdatates.enums.MovementType;
import com.nttdatates.exception.ValidationException;
import com.nttdatates.presentation.presenter.MovementPresenter;
import com.nttdatates.presentation.presenter.MovementsReportPresenter;
import com.nttdatates.repository.BankAccountRepository;
import com.nttdatates.repository.ClientRepository;
import com.nttdatates.repository.MovementRepository;
import com.nttdatates.service.MovementService;
import com.nttdatates.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Movement getMovementById(UUID movementId) {
        Optional<Movement> movement = movementRepository.findById(movementId);
        if (movement.isPresent()) {
            return movement.get();
        } else {
            throw new ValidationException("Movement not found");
        }
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public MovementPresenter saveUpdateMovement(MovementPresenter movementPresenter) {
        Movement movement;
        UUID movementId = movementPresenter.getMovementId();
        if (movementPresenter.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("The amount of movement must be greater than 0");
        }

        if (movementPresenter.getBankAccountId() == null) {
            throw new ValidationException("Movement must have bankAccountId");
        }

        Optional<BankAccount> bankAccount = bankAccountRepository.findById(movementPresenter.getBankAccountId());
        if (bankAccount.isEmpty()) {
            throw new ValidationException("Bank account not found");
        }
        if (movementId == null) {
            movement = new Movement();
        } else {
            movement = movementRepository.findById(movementPresenter.getMovementId())
                    .orElse(new Movement());
        }

        movement.setMovementType(movementPresenter.getMovementType());
        movement.setDate(new Date());
        movement.setAmount(movementPresenter.getAmount());
        movement.setBankAccount(bankAccount.get());
        BigDecimal balance = getBalance(movement);
        movement.setBalance(balance);
        Movement movementSaved = movementRepository.save(movement);

        return toPresenter(movementSaved);

    }


    @Override
    @Transactional
    public void deleteMovementById(UUID movementId) {

        Optional<Movement> movement = movementRepository.findById(movementId);
        if (movement.isPresent()) {
            movementRepository.deleteById(movement.get().getMovementId());
        } else {
            throw new ValidationException("Movement not found");
        }
    }

    @Override
    public MovementPresenter toPresenter(Movement movement) {
        return MovementPresenter.builder()
                .movementId(movement.getMovementId())
                .movementType(movement.getMovementType())
                .date(movement.getDate())
                .amount(movement.getAmount())
                .balance(movement.getBalance())
                .bankAccountId(movement.getBankAccount().getBankAccountId())
                .build();

    }

    @Override
    public List<MovementsReportPresenter> getMovementByClientAndDates(UUID clientId, Date initDate, Date endDate) {
        List<MovementsReportPresenter> movementsReportPresenters = new ArrayList<>();
        if (initDate != null) {
            initDate = DateUtils.instance().asDate(
                    DateUtils.instance().asLocalDateTime(initDate)
                            .withHour(0).withSecond(0).withMinute(0).withNano(0)
            );
        }
        if (endDate != null) {
            endDate = DateUtils.instance().asDate(
                    DateUtils.instance().asLocalDateTime(endDate).withHour(23).withMinute(59).withSecond(59).withNano(0)
            );
        }
        if ((initDate != null && endDate != null) && initDate.compareTo(endDate) > 0) {
            throw new ValidationException("El rango de fechas es inválido");
        }
        Optional<Client> client = clientRepository.findById(clientId);
        if (!client.isPresent()) {
            throw new ValidationException("Client not found");
        }
        List<Object[]> movements = movementRepository.getMovementByClientAndDates(client.get().getPersonId(), initDate, endDate);
        movements.forEach(object ->
                movementsReportPresenters.add(MovementsReportPresenter.builder()
                        .date(object[0].toString())
                        .client((String) object[1])
                        .accountNumber((String) object[2])
                        .accountType((String) object[3])
                        .initialBalance((BigDecimal) object[4])
                        .status((String) object[5])
                        .movementAmount((BigDecimal) object[6])
                        .availableBalance((BigDecimal) object[7])
                        .build())
        );
        return movementsReportPresenters;
    }

    private BigDecimal calculateBalance(Movement movement) {
        AtomicReference<BigDecimal> balance = new AtomicReference<>(BigDecimal.ZERO);
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(movement.getBankAccount().getBankAccountId());
        bankAccount.get().getMovements().add(movement);
        bankAccount.get().getMovements().stream().sorted(Comparator.comparing(movement1 -> movement1.getDate()))
                .forEach(movement1 -> {
                    if (movement1.getMovementType().equals(MovementType.input)) {
                        balance.set(balance.get().add(movement1.getAmount()));
                    } else if (movement1.getMovementType().equals(MovementType.output)) {
                        balance.set(balance.get().subtract(movement1.getAmount()));
                    }
                });

        return balance.get();
    }

    private BigDecimal getBalance(Movement movement) {

        Optional<BankAccount> bankAccount = bankAccountRepository.findById(movement.getBankAccount().getBankAccountId());
        BigDecimal balance = bankAccount.get().getMovements().stream().sorted(Comparator.comparing(
                        movement1 -> ((Movement) movement1).getDate()).reversed())
                .findFirst().get().getBalance();
        BigDecimal mov = movement.getMovementType().equals(MovementType.input) ? balance.add(movement.getAmount()) : balance.subtract(movement.getAmount());
        if (mov.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Saldo no disponible");
        }
        return mov;
    }
}
