package com.nttdatates.service.impl;

import com.nttdatates.entity.Client;
import com.nttdatates.exception.ValidationException;
import com.nttdatates.presentation.presenter.BankAccountPresenter;
import com.nttdatates.presentation.presenter.ClientPresenter;
import com.nttdatates.repository.ClientRepository;
import com.nttdatates.service.BankAccountService;
import com.nttdatates.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BankAccountService bankAccountService;


    @Override
    public Client getClientById(UUID clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new ValidationException("Client not found");
        }
    }

    @Override
    public ClientPresenter getClientByIdentification(String identification) {
        Optional<Client> client = clientRepository.findByIdentification(identification);
        if (client.isPresent()) {
            return toPresenter(client.get());
        } else {
            throw new ValidationException("Client not found");
        }
    }

    @Override
    @Transactional
    public ClientPresenter saveClient(ClientPresenter clientPresenter) {
        String identification = clientPresenter.getIdentification();
        if (identification == null || identification.isEmpty() || identification.isBlank()) {
            throw new ValidationException("Client must have identification number");
        }
        if (identification.length() != 10) {
            throw new ValidationException("Identification length should be 10");
        }
        Optional<Client> optionalClient = clientRepository.findByIdentification(identification);
        if (optionalClient.isPresent()) {
            throw new ValidationException("Client has already been registered");
        } else {
            Client client = new Client();
            client.setName(clientPresenter.getName());
            client.setGender(clientPresenter.getGender());
            client.setAge(clientPresenter.getAge());
            client.setIdentification(clientPresenter.getIdentification());
            client.setDirection(clientPresenter.getDirection());
            client.setPhone(clientPresenter.getPhone());
            client.setPassword(clientPresenter.getPassword());
            client.setActive(clientPresenter.getActive());
            Client clientSaved = clientRepository.save(client);
            return toPresenter(clientSaved);
        }
    }

    @Override
    @Transactional
    public ClientPresenter updateClient(ClientPresenter clientPresenter) {
        UUID clientId = clientPresenter.getPersonId();
        if (clientId == null) {
            throw new ValidationException("Client must have ID");
        }
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            throw new ValidationException("Client not found");
        } else {
            if (clientPresenter.getIdentification().length() != 10) {
                throw new ValidationException("Identification length should be 10");
            }
            client.get().setName(clientPresenter.getName());
            client.get().setGender(clientPresenter.getGender());
            client.get().setAge(clientPresenter.getAge());
            client.get().setIdentification(clientPresenter.getIdentification());
            client.get().setDirection(clientPresenter.getDirection());
            client.get().setPhone(clientPresenter.getPhone());
            client.get().setPassword(clientPresenter.getPassword());
            client.get().setActive(clientPresenter.getActive());
            Client clientSaved = clientRepository.save(client.get());
            return toPresenter(clientSaved);
        }
    }

    @Override
    @Transactional
    public void deleteClientById(UUID clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isPresent()) {
            if (!client.get().getBankAccounts().isEmpty()) {
                throw new ValidationException("The bank account has movement");
            }
            clientRepository.deleteById(client.get().getPersonId());

        } else {
            throw new ValidationException("Client not found");
        }
    }

    @Override
    public ClientPresenter toPresenter(Client client) {
        Set<BankAccountPresenter> bankAccountPresenters = new HashSet<>();
        if (client.getBankAccounts() != null) {
            client.getBankAccounts().forEach(bankAccount -> {
                bankAccountPresenters.add(bankAccountService.toPresenter(bankAccount));
            });
        }

        return ClientPresenter.builder()
                .personId(client.getPersonId())
                .name(client.getName())
                .gender(client.getGender())
                .age(client.getAge())
                .identification(client.getIdentification())
                .direction(client.getDirection())
                .phone(client.getPhone())
                .password(client.getPassword())
                .active(client.getActive())
                .bankAccountPresenters(bankAccountPresenters)
                .build();

    }
}
