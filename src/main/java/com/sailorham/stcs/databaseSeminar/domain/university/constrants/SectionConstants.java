package com.sailorham.stcs.databaseSeminar.domain.university.constrants;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SectionConstants {

    public static final List<String> VALID_SEMESTERS = List.of(
        "String", "Summer", "Fall", "Winter"
    );
}
