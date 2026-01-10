package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.PrereqId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
@Table(name = "prereq")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Prereq {

    @EmbeddedId
    PrereqId prereqId;

    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Course course;

    @MapsId("prereqId")
    @JoinColumn(name = "prereq_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    Course prereq;

    @Builder
    public Prereq(Course course, Course prereq) {
        this.prereqId = new PrereqId(course.getCourseId(), prereq.getCourseId());
        this.course = course;
        this.prereq = prereq;
    }
}
