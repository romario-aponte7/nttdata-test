package com.nttdatates.service;

import com.nttdatates.entity.Movement;
import com.nttdatates.presentation.presenter.MovementPresenter;
import com.nttdatates.presentation.presenter.MovementsReportPresenter;

import java.util.*;

public interface MovementService {

    Movement getMovementById(UUID movementId);

    MovementPresenter saveUpdateMovement(MovementPresenter movementPresenter);

    void deleteMovementById(UUID movementId);

    MovementPresenter toPresenter(Movement movement);

    List<MovementsReportPresenter> getMovementByClientAndDates(UUID clientId, Date initDate, Date endDate);
}
