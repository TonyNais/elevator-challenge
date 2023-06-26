package io.elevator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "executed_query_log")
@Data @AllArgsConstructor @ToString
public class ExecutedQueryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "query", nullable = false)
    private String query;

    public ExecutedQueryLog() {
        this.timestamp = LocalDateTime.now();
    }

    public ExecutedQueryLog(String location, String query, String username) {
        this.timestamp = LocalDateTime.now();
        this.location = location;
        this.query = query;
        this.username = username;
    }
}
