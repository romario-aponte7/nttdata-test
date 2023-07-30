package com.nttdatates.repository;

import com.nttdatates.entity.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, UUID> {

    Optional<BankAccount> findByNumber(String number);

}
