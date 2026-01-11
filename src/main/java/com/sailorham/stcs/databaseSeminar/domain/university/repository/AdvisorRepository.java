package com.sailorham.stcs.databaseSeminar.domain.university.repository;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvisorRepository extends JpaRepository<Advisor, String> {

}
