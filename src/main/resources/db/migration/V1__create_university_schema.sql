-- 강의실 테이블
CREATE TABLE classroom
(
    building    VARCHAR(15) NOT NULL COMMENT '건물명',
    room_number VARCHAR(7)  NOT NULL COMMENT '강의실 번호',
    capacity    INTEGER COMMENT '수용 인원',

    PRIMARY KEY pk_classroom (building, room_number)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '강의실';

-- 학과 테이블
CREATE TABLE department
(
    dept_name VARCHAR(20) NOT NULL COMMENT '학과명',
    building  VARCHAR(15) COMMENT '건물명',
    budget    DECIMAL(12, 2) COMMENT '학과 예산',

    PRIMARY KEY pk_department (dept_name),

    CONSTRAINT check_department_budget CHECK ( budget > 0 )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '학과';

-- 강의 테이블
CREATE TABLE course
(
    course_id VARCHAR(8)  NOT NULL COMMENT '강의 ID',
    title     VARCHAR(50) NOT NULL COMMENT '강의명',
    dept_name VARCHAR(20) COMMENT '학과명',
    credits   INTEGER COMMENT '학점',

    PRIMARY KEY pk_course (course_id),

    CONSTRAINT fk_course_dept_name
        FOREIGN KEY (dept_name)
            REFERENCES department (dept_name)
            ON DELETE SET NULL,

    CONSTRAINT check_course_credits CHECK ( credits > 0 )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '강의';

-- 교수 테이블
CREATE TABLE instructor
(
    instructor_id VARCHAR(5)  NOT NULL COMMENT '교수 ID',
    name          VARCHAR(20) NOT NULL COMMENT '교수명',
    dept_name     VARCHAR(20) COMMENT '학과명',
    salary        DECIMAL(8, 2) COMMENT '월급',

    PRIMARY KEY pk_instructor (instructor_id),

    CONSTRAINT fk_instructor_dept_name
        FOREIGN KEY (dept_name)
            REFERENCES department (dept_name)
            ON DELETE SET NULL,

    CONSTRAINT check_instructor_salary CHECK ( salary > 29000 )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '교수';

-- 개설 강좌 테이블
CREATE TABLE section
(
    course_id    VARCHAR(8) NOT NULL COMMENT '강의 ID',
    sec_id       VARCHAR(8) NOT NULL COMMENT '분반 ID',
    semester     VARCHAR(6) NOT NULL COMMENT '학기',
    year         INTEGER    NOT NULL COMMENT '년도',
    building     VARCHAR(15) COMMENT '건물명',
    room_number  VARCHAR(7) COMMENT '강의실 번호',
    time_slot_id VARCHAR(4) COMMENT '수업 교시 ID (논리적 참조)',

    PRIMARY KEY pk_section (course_id, sec_id, semester, year),

    CONSTRAINT fk_section_course_id
        FOREIGN KEY (course_id)
            REFERENCES course (course_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_section_classroom
        FOREIGN KEY (building, room_number)
            REFERENCES classroom (building, room_number)
            ON DELETE SET NULL,

    CONSTRAINT check_section_semester CHECK ( semester IN ('Spring', 'Summer', 'Fall', 'Winter') ),
    CONSTRAINT check_section_year CHECK ( year > 1701 AND year < 2100 )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '개설 강좌';

-- 강의 담당 테이블
CREATE TABLE teaches
(
    instructor_id VARCHAR(5) NOT NULL COMMENT '교수 ID',
    course_id     VARCHAR(8) NOT NULL COMMENT '강의 ID',
    sec_id        VARCHAR(8) NOT NULL COMMENT '분반 ID',
    semester      VARCHAR(6) NOT NULL COMMENT '학기',
    year          INTEGER    NOT NULL COMMENT '년도',

    PRIMARY KEY pk_teaches (instructor_id, course_id, sec_id, semester, year),

    CONSTRAINT fk_teaches_instructor_id
        FOREIGN KEY (instructor_id)
            REFERENCES instructor (instructor_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_teaches_section
        FOREIGN KEY (course_id, sec_id, semester, year)
            REFERENCES section (course_id, sec_id, semester, year)
            ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '강의 담당';

-- 학생 테이블
CREATE TABLE student
(
    student_id VARCHAR(5)  NOT NULL COMMENT '학생 ID',
    name       VARCHAR(20) NOT NULL COMMENT '학생명',
    dept_name  VARCHAR(20) COMMENT '학과명',
    tot_cred   INTEGER COMMENT '총 이수 학점',

    PRIMARY KEY pk_student (student_id),

    CONSTRAINT fk_student_dept_name
        FOREIGN KEY (dept_name)
            REFERENCES department (dept_name)
            ON DELETE SET NULL,

    CONSTRAINT check_student_tot_cred CHECK ( tot_cred >= 0 )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '학생';

-- 수강 신청 테이블
CREATE TABLE takes
(
    student_id VARCHAR(5) NOT NULL COMMENT '학생 ID',
    course_id  VARCHAR(8) NOT NULL COMMENT '강의 ID',
    sec_id     VARCHAR(8) NOT NULL COMMENT '분반 ID',
    semester   VARCHAR(6) NOT NULL COMMENT '학기',
    year       INTEGER    NOT NULL COMMENT '년도',
    grade      VARCHAR(2) COMMENT '성적',

    PRIMARY KEY pk_takes (student_id, course_id, sec_id, semester, year),

    CONSTRAINT fk_takes_student_id
        FOREIGN KEY (student_id)
            REFERENCES student (student_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_takes_section
        FOREIGN KEY (course_id, sec_id, semester, year)
            REFERENCES section (course_id, sec_id, semester, year)
            ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '수강 신청';

-- 지도 교수 테이블
CREATE TABLE advisor
(
    student_id    VARCHAR(5) NOT NULL COMMENT '학생 ID',
    instructor_id VARCHAR(5) COMMENT '교수 ID',

    PRIMARY KEY pk_advisor (student_id),

    CONSTRAINT fk_advisor_student_id
        FOREIGN KEY (student_id)
            REFERENCES student (student_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_advisor_instructor_id
        FOREIGN KEY (instructor_id)
            REFERENCES instructor (instructor_id)
            ON DELETE SET NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '지도 교수';

-- 시간표 테이블 - 논리적 참조용
CREATE TABLE time_slot
(
    time_slot_id VARCHAR(4) NOT NULL COMMENT '수업 교시 ID',
    day          VARCHAR(1) NOT NULL COMMENT '요일',
    start_hr     INTEGER    NOT NULL COMMENT '시작 시',
    start_min    INTEGER    NOT NULL COMMENT '시작 분',
    end_hr       INTEGER COMMENT '종료 시',
    end_min      INTEGER COMMENT '종료 분',

    PRIMARY KEY pk_time_slot (time_slot_id, day, start_hr, start_min),

    CONSTRAINT check_time_slot_start_hr CHECK ( start_hr >= 0 AND start_hr < 24),
    CONSTRAINT check_time_slot_start_min CHECK ( start_min >= 0 AND start_min < 60 ),
    CONSTRAINT check_time_slot_end_hr CHECK ( end_hr >= 0 AND end_hr < 24 ),
    CONSTRAINT check_time_slot_end_min CHECK ( end_min >= 0 AND end_min < 60 )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '시간표';

-- 선수 과목 테이블
CREATE TABLE prereq
(
    course_id VARCHAR(8) NOT NULL COMMENT '강의 ID',
    prereq_id VARCHAR(8) NOT NULL COMMENT '선수 과목 ID',

    PRIMARY KEY pk_prereq (course_id, prereq_id),

    CONSTRAINT fk_prereq_course_id
        FOREIGN KEY (course_id)
            REFERENCES course (course_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_prereq_prereq_id
        FOREIGN KEY (prereq_id)
            REFERENCES course (course_id)
            ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC
    COMMENT '선수 과목';
