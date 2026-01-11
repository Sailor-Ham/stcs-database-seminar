package com.sailorham.stcs.databaseSeminar.domain.university.repository;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.Prereq;
import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.PrereqId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrereqRepository extends JpaRepository<Prereq, PrereqId> {

}
