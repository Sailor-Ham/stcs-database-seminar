package com.sailorham.stcs.databaseSeminar.domain.university.repository;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.Classroom;
import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.ClassroomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, ClassroomId> {

}
