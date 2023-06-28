package io.elevator.controller;

import io.elevator.dto.ElevatorStatusDTO;
import io.elevator.service.ElevatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/elevator")
@Slf4j
public class ElevatorController {
    private final ElevatorService elevatorService;

    @Autowired
    public ElevatorController(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    @PostMapping("/call")
    public void callElevator(@RequestParam("elevatorId") int elevatorId,
                             @RequestParam("sourceFloor") int sourceFloor,
                             @RequestParam("destinationFloor") int destinationFloor) {

        log.info("Calling elevator {} from floor {} to floor {}",elevatorId,sourceFloor,destinationFloor);
        elevatorService.moveElevator(elevatorId, sourceFloor, destinationFloor);
    }

    @GetMapping("/{elevatorId}/status")
    public ElevatorStatusDTO getElevatorStatus(@PathVariable("elevatorId") int elevatorId) {
        log.info("Getting status information for elevator {}",elevatorId);
        return elevatorService.getElevatorStatus(elevatorId);
    }

    @GetMapping("/status")
    public Map<Integer, ElevatorStatusDTO> getAllElevatorsStatus() {
        log.info("Getting status information for all elevators");
        return elevatorService.getAllElevatorsStatus();
    }
}
