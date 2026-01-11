package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.TakesId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "takes")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Takes {

    @EmbeddedId
    TakesId takesId;

    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Student student;

    @MapsId("sectionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "course_id"),
        @JoinColumn(name = "sec_id"),
        @JoinColumn(name = "semester"),
        @JoinColumn(name = "year")
    })
    Section section;

    @Column(name = "grade", length = 2)
    String grade;

    @Builder
    public Takes(Student student, Section section, String grade) {
        this.takesId = new TakesId(student.getStudentId(), section.getSectionId());
        this.student = student;
        this.section = section;
        this.grade = grade;
    }
}
