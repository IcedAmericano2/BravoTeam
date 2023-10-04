package com.mju.management.domain.model.Exception.repository;

import com.mju.management.domain.model.CheckList;
import com.mju.management.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
