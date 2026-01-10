package com.sailorham.stcs.databaseSeminar.domain.university.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class SectionId implements Serializable {

    @Column(name = "course_id", length = 8)
    String courseId;

    @Column(name = "sec_id", length = 8)
    String secId;

    @Column(name = "semester", length = 6)
    String semester;

    @Column(name = "year", precision = 4)
    BigDecimal year;
}
