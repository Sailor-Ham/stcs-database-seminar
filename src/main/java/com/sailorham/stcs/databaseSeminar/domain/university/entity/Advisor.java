package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "advisor")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Advisor {

    @Id
    @Column(name = "s_id", length = 5)
    String sId;

    @MapsId("sId")
    @JoinColumn(name = "s_id")
    @OneToOne(fetch = FetchType.LAZY)
    Student student;

    @JoinColumn(name = "i_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Instructor instructor;

    @Builder
    public Advisor(Student student, Instructor instructor) {
        this.student = student;
        this.instructor = instructor;
    }
}
