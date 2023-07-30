package com.nttdatates.presentation.controller;

import com.nttdatates.presentation.presenter.ClientPresenter;
import com.nttdatates.service.ClientService;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Generated
@RestController

public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/getClientById")
    public ClientPresenter getClientById(@RequestParam("clientId") UUID clientId) {
        return clientService.toPresenter(clientService.getClientById(clientId));
    }

    @GetMapping("/getClientByIdentification")
    public ClientPresenter getClientByIdentification(@RequestParam("identification") String identification) {
        return clientService.getClientByIdentification(identification);
    }

    @PostMapping("/postClient")
    public ClientPresenter saveClient(@Valid @RequestBody ClientPresenter clientPresenter) {
        return clientService.saveClient(clientPresenter);
    }

    @PutMapping("/putClient")
    public ClientPresenter updateClient(@RequestBody ClientPresenter clientPresenter) {
        return clientService.updateClient(clientPresenter);
    }

    @DeleteMapping("/deleteClientById")
    public void deleteClientById(@RequestParam("clientId") UUID clientId) {
        clientService.deleteClientById(clientId);
    }

}
