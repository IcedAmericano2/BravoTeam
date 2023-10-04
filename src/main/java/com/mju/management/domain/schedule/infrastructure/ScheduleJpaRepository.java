package com.mju.management.domain.schedule.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {
}
