package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import com.sailorham.stcs.databaseSeminar.common.exception.ServiceException;
import com.sailorham.stcs.databaseSeminar.common.exception.ServiceExceptionCode;
import com.sailorham.stcs.databaseSeminar.domain.university.entity.id.TimeSlotId;
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
@Table(name = "time_slot")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeSlot {

    @EmbeddedId
    TimeSlotId timeSlotId;

    @Column(name = "end_hr", precision = 2)
    Integer endHr;

    @Column(name = "end_min", precision = 2)
    Integer endMin;

    @Builder
    public TimeSlot(
        String timeSlotId,
        String day,
        Integer startHr,
        Integer startMin,
        Integer endHr,
        Integer endMin
    ) {

        validateTime(startHr, startMin);

        if (endHr != null && endMin != null) {
            validateTime(endHr, endMin);
        }

        this.timeSlotId = new TimeSlotId(timeSlotId, day, startHr, startMin);
        this.endHr = endHr;
        this.endMin = endMin;
    }

    void validateTime(Integer hr, Integer min) {

        if (hr == null || min == null) {
            return;
        }

        boolean isHourInvalid = hr < 0 || hr >= 24;
        boolean isMinInvalid = min < 0 || min >= 60;

        if (isHourInvalid || isMinInvalid) {
            throw new ServiceException(ServiceExceptionCode.INVALID_TIME_FORMAT);
        }
    }
}
