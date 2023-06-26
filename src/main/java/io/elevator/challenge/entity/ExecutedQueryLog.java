package io.elevator.challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "executed_query_log")
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class ExecutedQueryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "user", nullable = false)
    private String user;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "query", nullable = false)
    private String query;

    // Constructors, getters, and setters
}
