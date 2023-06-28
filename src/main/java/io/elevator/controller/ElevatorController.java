package io.elevator.controller;

import io.elevator.dto.ElevatorStatusDTO;
import io.elevator.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/elevator")
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
        elevatorService.moveElevator(elevatorId, sourceFloor, destinationFloor);
    }

    @GetMapping("/{elevatorId}/status")
    public ElevatorStatusDTO getElevatorStatus(@PathVariable("elevatorId") int elevatorId) {
        return elevatorService.getElevatorStatus(elevatorId);
    }

    @GetMapping("/status")
    public Map<Integer, ElevatorStatusDTO> getAllElevatorsStatus() {
        return elevatorService.getAllElevatorsStatus();
    }
}
