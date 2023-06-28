package io.elevator.dto;

import io.elevator.util.ElevatorDirection;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ElevatorStatusDTO {
    private int elevatorId;
    private int currentFloor;
    private ElevatorDirection direction;
    private String lastEvent;

    public ElevatorStatusDTO(int elevatorId) {
        this.elevatorId = elevatorId;
    }

}
