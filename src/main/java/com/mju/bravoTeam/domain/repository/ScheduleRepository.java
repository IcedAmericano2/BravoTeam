package com.mju.bravoTeam.domain.repository;

import com.mju.bravoTeam.domain.model.CheckList;
import com.mju.bravoTeam.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
