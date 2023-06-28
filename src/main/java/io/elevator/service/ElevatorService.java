package io.elevator.service;

import io.elevator.dto.ElevatorStatusDTO;
import io.elevator.entity.ElevatorLog;
import io.elevator.entity.ExecutedQueryLog;
import io.elevator.repository.ElevatorLogRepository;
import io.elevator.repository.ExecutedQueryLogRepository;
import io.elevator.util.DoorStatusEnum;
import io.elevator.util.ElevatorDirectionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
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
        if(sourceFloor == destinationFloor){
            elevatorLog.setDirection(ElevatorDirectionEnum.STATIONARY);
        }else if(sourceFloor>destinationFloor){
            elevatorLog.setDirection(ElevatorDirectionEnum.MOVING_UP);
        }else {
            elevatorLog.setDirection(ElevatorDirectionEnum.MOVING_DOWN);
        }
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
                    // Delay for 5 seconds to simulate moving between floors
                    Thread.sleep(5000);
                    log.info("Elevator {} now at floor {}", elevatorId, currentFloor);

                    // Adjust current floor based on direction
                    currentFloor += (currentFloor < destinationFloor) ? 1 : -1;

                    elevatorStatus.setCurrentFloor(currentFloor);
                    logElevatorEvent(elevatorLog);

                    if (currentFloor == destinationFloor) {
                        log.info("Elevator {} has arrived at floor {}", elevatorId, destinationFloor);
                        elevatorStatus.setDirection(ElevatorDirectionEnum.STATIONARY);
                        elevatorStatus.setDoorStatus(DoorStatusEnum.OPEN);
                        elevatorStatus.setLastEvent("Arrived at floor " + destinationFloor);
                        logElevatorEvent(elevatorLog);

                        //Door open for two seconds
                        Thread.sleep(2000);

                        //Door closed after two seconds
                        elevatorStatus.setDoorStatus(DoorStatusEnum.CLOSED);
                        logElevatorEvent(elevatorLog);
                    }
                }
            } catch (InterruptedException e) {
                log.error(e.getLocalizedMessage());
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

    private ElevatorDirectionEnum getDirection(int currentFloor, int destinationFloor) {
        if (currentFloor < destinationFloor) {
            return ElevatorDirectionEnum.MOVING_UP;
        } else if (currentFloor > destinationFloor) {
            return ElevatorDirectionEnum.MOVING_DOWN;
        } else {
            return ElevatorDirectionEnum.STATIONARY;
        }
    }

    public  Map<Integer, ElevatorStatusDTO> getAllElevatorsStatus() {
        return elevatorStatusMap;
    }
}
