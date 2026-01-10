package com.sailorham.stcs.databaseSeminar.domain.university.repository;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.Takes;
import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.TakesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TakesRepository extends JpaRepository<Takes, TakesId> {

}
