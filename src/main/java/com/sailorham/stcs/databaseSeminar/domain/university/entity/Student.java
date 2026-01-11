package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import com.sailorham.stcs.databaseSeminar.common.exception.ServiceException;
import com.sailorham.stcs.databaseSeminar.common.exception.ServiceExceptionCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "student")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @Column(name = "student_id", length = 5)
    String studentId;

    @Column(name = "name", nullable = false, length = 20)
    String name;

    @JoinColumn(name = "dept_name")
    @ManyToOne(fetch = FetchType.LAZY)
    Department department;

    @Column(name = "tot_cred")
    Integer totCred;

    @Builder
    public Student(String studentId, String name, Department department, Integer totCred) {

        if (totCred != null && totCred < 0) {
            throw new ServiceException(ServiceExceptionCode.INVALID_STUDENT_TOT_CRED);
        }

        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.totCred = totCred;
    }
}
