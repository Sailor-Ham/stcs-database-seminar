package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.TeachesId;
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
@Table(name = "teaches")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teaches {

    @EmbeddedId
    TeachesId teachesId;

    @MapsId("instructorId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    Instructor instructor;


    @MapsId("sectionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "course_id"),
        @JoinColumn(name = "sec_id"),
        @JoinColumn(name = "semester"),
        @JoinColumn(name = "year")
    })
    Section section;

    @Builder
    public Teaches(Instructor instructor, Section section) {
        this.teachesId = new TeachesId(instructor.getInstructorId(), section.getSectionId());
        this.instructor = instructor;
        this.section = section;
    }
}
