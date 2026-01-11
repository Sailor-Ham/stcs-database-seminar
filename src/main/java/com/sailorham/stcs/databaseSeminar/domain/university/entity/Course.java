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
@Table(name = "course")
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

    @Column(name = "credits")
    Integer credits;

    @Builder
    public Course(String courseId, String title, Department department, Integer credits) {

        if (credits != null && credits <= 0) {
            throw new ServiceException(ServiceExceptionCode.INVALID_COURSE_CREDITS);
        }

        this.courseId = courseId;
        this.title = title;
        this.department = department;
        this.credits = credits;
    }
}
