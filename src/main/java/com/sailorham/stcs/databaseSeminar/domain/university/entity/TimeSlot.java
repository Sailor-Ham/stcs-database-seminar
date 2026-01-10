package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.TimeSlotId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "time_slot")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeSlot {

    @EmbeddedId
    TimeSlotId timeSlotId;

    @Column(name = "end_time")
    LocalTime endTime;

    @Builder
    public TimeSlot(String timeSlotId, String day, LocalTime startTime, LocalTime endTime) {
        this.timeSlotId = new TimeSlotId(timeSlotId, day, startTime);
        this.endTime = endTime;
    }
}
