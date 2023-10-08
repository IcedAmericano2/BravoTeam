package com.mju.management.domain.schedule.infrastructure;

import com.mju.management.domain.project.infrastructure.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByProject(Project project);
}
