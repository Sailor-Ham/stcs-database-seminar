package com.sailorham.stcs.databaseSeminar.domain.university.entity;

import com.sailorham.stcs.databaseSeminar.common.exception.ServiceException;
import com.sailorham.stcs.databaseSeminar.common.exception.ServiceExceptionCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "department")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department {

    @Id
    @Column(name = "dept_name", length = 20)
    String deptName;

    @Column(name = "building", length = 15)
    String building;

    @Column(name = "budget", precision = 12, scale = 2)
    BigDecimal budget;

    @Builder
    public Department(String deptName, String building, BigDecimal budget) {

        if (budget != null && budget.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException(ServiceExceptionCode.INVALID_DEPARTMENT_BUDGET);
        }
        
        this.deptName = deptName;
        this.building = building;
        this.budget = budget;
    }
}
