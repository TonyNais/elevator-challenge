package io.elevator.repository;

import io.elevator.entity.ElevatorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElevatorLogRepository extends JpaRepository<ElevatorLog, Long> {

}
