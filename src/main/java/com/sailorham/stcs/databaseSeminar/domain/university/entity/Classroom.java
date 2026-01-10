package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.ClassroomId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
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

    @Column(name = "capacity", precision = 4)
    BigDecimal capacity;

    @Builder
    public Classroom(String building, String roomNumber, BigDecimal capacity) {
        this.classroomId = new ClassroomId(building, roomNumber);
        this.capacity = capacity != null ? capacity : BigDecimal.ZERO;
    }
}
