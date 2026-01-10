package com.sailorham.stcs.databaseSeminar.domain.university.repository;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.Teaches;
import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.TeachesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachesRepository extends JpaRepository<Teaches, TeachesId> {

}
