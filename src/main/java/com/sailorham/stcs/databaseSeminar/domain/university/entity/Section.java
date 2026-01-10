package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.SectionId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "section")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Section {

    @EmbeddedId
    SectionId sectionId;

    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "building", referencedColumnName = "building"),
        @JoinColumn(name = "room_number", referencedColumnName = "room_number")
    })
    Classroom classroom;

    @Column(name = "time_slot_id", length = 4)
    String timeSlotId;

    @Builder
    public Section(
        Course course,
        String secId,
        String semester,
        BigDecimal year,
        Classroom classroom,
        String timeSlotId
    ) {
        this.sectionId = new SectionId(course.getCourseId(), secId, semester, year);
        this.course = course;
        this.classroom = classroom;
        this.timeSlotId = timeSlotId;
    }
}
