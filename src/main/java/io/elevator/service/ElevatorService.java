package io.elevator.service;

import io.elevator.dto.ElevatorStatusDTO;
import io.elevator.entity.ElevatorLog;
import io.elevator.entity.ExecutedQueryLog;
import io.elevator.repository.ElevatorLogRepository;
import io.elevator.repository.ExecutedQueryLogRepository;
import io.elevator.util.ElevatorDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ElevatorService {
    private final ElevatorLogRepository elevatorLogRepository;
    private final ExecutedQueryLogRepository executedQueryLogRepository;

    // Map to store elevator status by ID
    private final Map<Integer, ElevatorStatusDTO> elevatorStatusMap;

    @Autowired
    public ElevatorService(ElevatorLogRepository elevatorLogRepository, ExecutedQueryLogRepository executedQueryLogRepository) {
        this.elevatorLogRepository = elevatorLogRepository;
        this.executedQueryLogRepository = executedQueryLogRepository;
        this.elevatorStatusMap = new HashMap<>();
    }

    public void moveElevator(int elevatorId, int sourceFloor, int destinationFloor) {
        ElevatorLog elevatorLog = new ElevatorLog();
        elevatorLog.setTimestamp(LocalDateTime.now());
        elevatorLog.setElevatorId(elevatorId);
        elevatorLog.setFloor(sourceFloor);
        elevatorLog.setDirection("Moving");
        elevatorLog.setEvent("Called from floor " + sourceFloor);
        logElevatorEvent(elevatorLog);

        ElevatorStatusDTO elevatorStatus = elevatorStatusMap.getOrDefault(elevatorId, new ElevatorStatusDTO(elevatorId));
        elevatorStatus.setCurrentFloor(sourceFloor);
        elevatorStatus.setDirection(getDirection(sourceFloor, destinationFloor));

        CompletableFuture.runAsync(() -> {
            // Simulate elevator movement
            try {
                int currentFloor = sourceFloor;
                while (currentFloor != destinationFloor) {
                    Thread.sleep(5000); // Delay for 5 seconds to simulate moving between floors

                    currentFloor += (currentFloor < destinationFloor) ? 1 : -1; // Adjust current floor based on direction

                    elevatorStatus.setCurrentFloor(currentFloor);
                    logElevatorEvent(elevatorLog);

                    if (currentFloor == destinationFloor) {
                        elevatorStatus.setDirection(ElevatorDirection.STATIONARY);
                        elevatorStatus.setLastEvent("Arrived at floor " + destinationFloor);
                        logElevatorEvent(elevatorLog);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        elevatorStatusMap.put(elevatorId, elevatorStatus);
    }

    public ElevatorStatusDTO getElevatorStatus(int elevatorId) {
        return elevatorStatusMap.getOrDefault(elevatorId, new ElevatorStatusDTO(elevatorId));
    }

    @Async
    public void logElevatorEvent(ElevatorLog elevatorLog) {
        elevatorLogRepository.save(elevatorLog);
    }

    public void logExecutedQuery(ExecutedQueryLog executedQueryLog) {
        executedQueryLogRepository.save(executedQueryLog);
    }

    private ElevatorDirection getDirection(int currentFloor, int destinationFloor) {
        if (currentFloor < destinationFloor) {
            return ElevatorDirection.UP;
        } else if (currentFloor > destinationFloor) {
            return ElevatorDirection.DOWN;
        } else {
            return ElevatorDirection.STATIONARY;
        }
    }

    public  Map<Integer, ElevatorStatusDTO> getAllElevatorsStatus() {
        return elevatorStatusMap;
    }
}
