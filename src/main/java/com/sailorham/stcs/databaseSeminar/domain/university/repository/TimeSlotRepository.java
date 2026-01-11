package com.sailorham.stcs.databaseSeminar.domain.university.repository;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.TimeSlot;
import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.TimeSlotId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, TimeSlotId> {

}
