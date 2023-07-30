package com.nttdatates.service;


import com.nttdatates.entity.Client;
import com.nttdatates.presentation.presenter.ClientPresenter;

import java.util.UUID;

public interface ClientService {

    Client getClientById(UUID clientId);

    ClientPresenter getClientByIdentification(String identification);

    ClientPresenter saveClient(ClientPresenter clientPresenter);

    ClientPresenter updateClient(ClientPresenter clientPresenter);

    void deleteClientById(UUID clientId);

    ClientPresenter toPresenter(Client client);

}
