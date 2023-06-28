package io.elevator.dto;

import io.elevator.util.DoorStatusEnum;
import io.elevator.util.ElevatorDirectionEnum;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ElevatorStatusDTO {
    private int elevatorId;
    private int currentFloor;
    private ElevatorDirectionEnum direction;
    private DoorStatusEnum doorStatus;
    private String lastEvent;

    public ElevatorStatusDTO(int elevatorId) {
        this.elevatorId = elevatorId;
    }

}
