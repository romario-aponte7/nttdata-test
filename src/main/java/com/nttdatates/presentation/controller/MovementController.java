package com.nttdatates.presentation.controller;

import com.nttdatates.presentation.presenter.MovementPresenter;
import com.nttdatates.presentation.presenter.MovementsReportPresenter;
import com.nttdatates.service.MovementService;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Generated
@RestController
public class MovementController {

    @Autowired
    private MovementService movementService;

    @GetMapping("/getMovementById")
    public MovementPresenter getMovementById(@RequestParam("movementId") UUID movementId) {
        return movementService.toPresenter(movementService.getMovementById(movementId));
    }

    @PostMapping("/saveUpdateMovement")
    public MovementPresenter saveUpdateMovement(@RequestBody MovementPresenter movementPresenter) {
        return movementService.saveUpdateMovement(movementPresenter);
    }

    @DeleteMapping("/deleteMovement")
    public void deleteMovementById(@RequestParam("movementId") UUID movementId) {
        movementService.deleteMovementById(movementId);
    }

    @GetMapping("/getMovementByClientAndDates")
    public List<MovementsReportPresenter> getMovementByClientAndDates(@RequestParam("clientId") UUID clientId,
                                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date initDate,
                                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        return movementService.getMovementByClientAndDates(clientId,initDate,endDate);
    }
}
