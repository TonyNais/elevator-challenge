package io.elevator.repository;

import io.elevator.entity.ExecutedQueryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutedQueryLogRepository extends JpaRepository<ExecutedQueryLog, Long> {

}
