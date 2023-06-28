package io.elevator.entity;

import io.elevator.util.DoorStatusEnum;
import io.elevator.util.ElevatorDirectionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "elevator_log")
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class ElevatorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "elevator_id", nullable = false)
    private int elevatorId;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Column(name = "direction", nullable = false)
    private ElevatorDirectionEnum direction;

    @Column(name = "door", nullable = false)
    private DoorStatusEnum doorStatus = DoorStatusEnum.CLOSED;

    @Column(name = "event", nullable = false)
    private String event;
}
