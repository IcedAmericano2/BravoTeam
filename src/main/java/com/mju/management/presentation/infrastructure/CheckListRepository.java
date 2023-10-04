package com.mju.management.presentation.infrastructure;

import com.mju.management.presentation.domain.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
}
