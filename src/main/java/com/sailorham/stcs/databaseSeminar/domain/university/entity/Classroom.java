package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.ClassroomId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "classroom")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Classroom {

    @EmbeddedId
    ClassroomId classroomId;

    @Column(name = "capacity")
    Integer capacity;

    @Builder
    public Classroom(String building, String roomNumber, Integer capacity) {
        this.classroomId = new ClassroomId(building, roomNumber);
        this.capacity = capacity;
    }
}
