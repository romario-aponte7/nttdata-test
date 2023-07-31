package com.nttdatates.service.impl;

import com.nttdatates.entity.Client;
import com.nttdatates.exception.ValidationException;
import com.nttdatates.presentation.presenter.ClientPresenter;
import com.nttdatates.repository.ClientRepository;
import com.nttdatates.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClientById() {
        UUID clientId = UUID.randomUUID();
        Client mockClient = new Client();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(mockClient));

        Client result = clientService.getClientById(clientId);

        assertNotNull(result);
        assertEquals(mockClient, result);
    }

    @Test
    void testGetClientByIdentification() {
        String identification = "1234567890";
        Client mockClient = new Client();
        when(clientRepository.findByIdentification(identification)).thenReturn(Optional.of(mockClient));

        ClientPresenter result = clientService.getClientByIdentification(identification);

        assertNotNull(result);
    }


    @Test
    void testToPresenter() {
        Client mockClient = new Client();
        mockClient.setName("RON");
        mockClient.setGender("FEMALE");
        mockClient.setAge(15);

        ClientPresenter result = clientService.toPresenter(mockClient);

        assertNotNull(result);
    }
}
