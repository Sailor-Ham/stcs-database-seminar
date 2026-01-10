package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "instructor")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Instructor {

    @Id
    @Column(name = "instructor_id", length = 5)
    String instructorId;

    @Column(name = "name", nullable = false, length = 20)
    String name;

    @JoinColumn(name = "dept_name")
    @ManyToOne(fetch = FetchType.LAZY)
    Department department;

    @Column(name = "salary", precision = 8, scale = 2)
    BigDecimal salary;

    @Builder
    public Instructor(String instructorId, String name, Department department, BigDecimal salary) {
        this.instructorId = instructorId;
        this.name = name;
        this.department = department;
        this.salary = salary != null ? salary : BigDecimal.ZERO;
    }
}
