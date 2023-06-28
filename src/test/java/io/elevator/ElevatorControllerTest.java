package io.elevator;

import io.elevator.controller.ElevatorController;
import io.elevator.dto.ElevatorStatusDTO;
import io.elevator.service.ElevatorService;
import io.elevator.util.ElevatorDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ElevatorControllerTest {
    @Mock
    private ElevatorService elevatorService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ElevatorController elevatorController = new ElevatorController(elevatorService);
        mockMvc = MockMvcBuilders.standaloneSetup(elevatorController).build();
    }

    @Test
    void testCallElevator() throws Exception {
        // Arrange
        int elevatorId = 1;
        int sourceFloor = 1;
        int destinationFloor = 5;

        // Act
        mockMvc.perform(post("/elevator/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("elevatorId", String.valueOf(elevatorId))
                        .param("sourceFloor", String.valueOf(sourceFloor))
                        .param("destinationFloor", String.valueOf(destinationFloor)))
                        .andExpect(status().isOk());

        // Assert
        verify(elevatorService, times(1)).moveElevator(elevatorId, sourceFloor, destinationFloor);
    }

    @Test
    void testGetElevatorStatus() throws Exception {
        // Arrange
        int elevatorId = 1;
        ElevatorStatusDTO elevatorStatus = new ElevatorStatusDTO(elevatorId);
        elevatorStatus.setCurrentFloor(3);
        elevatorStatus.setDirection(ElevatorDirection.UP);

        when(elevatorService.getElevatorStatus(anyInt())).thenReturn(elevatorStatus);

        // Act
        MvcResult result = mockMvc.perform(get("/elevator/{elevatorId}/status", elevatorId))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String responseJson = result.getResponse().getContentAsString();
        Assertions.assertEquals("{\"elevatorId\":1,\"currentFloor\":3,\"direction\":\"UP\",\"lastEvent\":null}", responseJson);
    }
}
