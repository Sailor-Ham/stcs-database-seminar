package com.sailorham.stcs.databaseSeminar.domain.university.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeSlotId implements Serializable {

    @Column(name = "time_slot_id", length = 4)
    String timeSlotId;

    @Column(name = "day", length = 1)
    String day;

    @Column(name = "start_hr", precision = 2)
    Integer startHr;

    @Column(name = "start_min", precision = 2)
    Integer startMin;
}
