package com.sendkar.planpurchase.repository;

import com.sendkar.planpurchase.model.Status;
import com.sendkar.planpurchase.model.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByName(StatusName statusName);
}
