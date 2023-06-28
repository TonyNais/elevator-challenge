package io.elevator;

import io.elevator.dto.ElevatorStatusDTO;
import io.elevator.entity.ElevatorLog;
import io.elevator.repository.ElevatorLogRepository;
import io.elevator.service.ElevatorService;
import io.elevator.util.ElevatorDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ElevatorServiceTest {
	@Mock
	private ElevatorLogRepository elevatorLogRepository;

	@InjectMocks
	private ElevatorService elevatorService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testMoveElevator() {
		// Arrange
		int elevatorId = 1;
		int sourceFloor = 1;
		int destinationFloor = 5;

		// Act
		elevatorService.moveElevator(elevatorId, sourceFloor, destinationFloor);

		// Assert
		// Verify that elevatorLogRepository.save method is called once
		verify(elevatorLogRepository, times(1)).save(any(ElevatorLog.class));
	}

	@Test
	void testGetElevatorStatus() {
		// Arrange
		int elevatorId = 1;
		int sourceFloor = 1;
		int destinationFloor = 5;

		// Act
		elevatorService.moveElevator(elevatorId, sourceFloor, destinationFloor);
		ElevatorStatusDTO elevatorStatus = elevatorService.getElevatorStatus(elevatorId);

		// Assert
		Assertions.assertEquals(sourceFloor, elevatorStatus.getCurrentFloor());
		Assertions.assertEquals(ElevatorDirection.UP, elevatorStatus.getDirection());
	}

	@Test
	void testMoveElevatorSameFloor() {
		// Arrange
		int elevatorId = 1;
		int sourceFloor = 1;
		int destinationFloor = 1;

		// Act
		elevatorService.moveElevator(elevatorId, sourceFloor, destinationFloor);
		ElevatorStatusDTO elevatorStatus = elevatorService.getElevatorStatus(elevatorId);

		// Assert
		Assertions.assertEquals(sourceFloor, elevatorStatus.getCurrentFloor());
		Assertions.assertEquals(ElevatorDirection.STATIONARY, elevatorStatus.getDirection());
	}

	@Test
	void testMoveElevatorDownDirection() {
		// Arrange
		int elevatorId = 1;
		int sourceFloor = 5;
		int destinationFloor = 1;

		// Act
		elevatorService.moveElevator(elevatorId, sourceFloor, destinationFloor);
		ElevatorStatusDTO elevatorStatus = elevatorService.getElevatorStatus(elevatorId);

		// Assert
		Assertions.assertEquals(sourceFloor, elevatorStatus.getCurrentFloor());
		Assertions.assertEquals(ElevatorDirection.DOWN, elevatorStatus.getDirection());
	}
}
