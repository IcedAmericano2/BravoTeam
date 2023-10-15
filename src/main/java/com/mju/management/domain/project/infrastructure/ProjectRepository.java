package com.mju.management.domain.project.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	Optional<Project> findByProjectIndex(Long projectId);

	@Query("select p from Project p left join fetch p.scheduleList where p.projectIndex = :projectId")
	Optional<Project> findByIdWithScheduleList(@Param("projectId") Long projectId);
}
