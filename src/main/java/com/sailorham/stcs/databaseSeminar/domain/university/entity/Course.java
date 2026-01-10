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
public class Course {

    @Id
    @Column(name = "course_id", length = 8)
    String courseId;

    @Column(name = "title", nullable = false, length = 50)
    String title;

    @JoinColumn(name = "dept_name")
    @ManyToOne(fetch = FetchType.LAZY)
    Department department;

    @Column(name = "credits", precision = 2)
    BigDecimal credits;

    @Builder
    public Course(String courseId, String title, Department department, BigDecimal credits) {
        this.courseId = courseId;
        this.title = title;
        this.department = department;
        this.credits = credits != null ? credits : BigDecimal.ZERO;
    }
}
