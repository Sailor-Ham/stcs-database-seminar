# <img src="docs/images/stu_logo.png" height="40" align="left">서울신학대학교 일반대학원 IT융합학과 데이터베이스 세미나<br clear="left"/>

## 📘 프로젝트: **데이터베이스 시스템 세미나** 교재 DB 구현

이 프로젝트는 **데이터베이스 시스템 세미나** 수업의 연구 및 실습을 위해 구축된 백엔드 애플리케이션입니다.

---

### ⚖️ 출처 및 참고

본 프로젝트의 데이터베이스 스키마(Schema) 구조는 데이터베이스 분야의 표준 교재인 **[Database System Concepts]** (Abraham
Silberschatz, Henry F. Korth, S. Sudarshan 저)의 **University Database** 예제를 기반으로 구현되었습니다.

- **Schema Source:** *Database System Concepts (7th Edition)* Official Resources.
- **Purpose:** 본 프로젝트는 저자가 공식 제공하는 **Large Relations Dataset** (`largeRelationsInsertFile.sql`)과
  **DDL Script**를 MySQL에 맞춰 사용하였습니다.
- **License Notice:** 해당 데이터셋과 스키마 구조는 오직 **교육, 연구 및 세미나 실습 목적**으로만 사용됩니다.
- **Official Website:** [db-book.com](https://www.db-book.com/)

---

### 🛠 기술 스택

최신 Java 생태계를 기반으로 구성되었습니다.

- **Language:** Java 21 (LTS)
- **Framework:** Spring Boot 3.x
- **Build Tool:** Gradle (Groovy)
- **Database:** MySQL 8.0
- **ORM:** Spring Data JPA (Hibernate)
- **Infra:** Docker, Docker Compose

---

### ⚙️ 환경 설정

이 프로젝트는 민감한 정보(DB 접속 정보 등)를 `.env` 파일로 관리합니다.

프로젝트 루트 경로에 `.env` 파일을 생성하고 아래 내용을 작성해 주세요.

**`.env` 예시**

```properties
# MySQL 포트 설정 (기본값: 3306)
# 로컬 개발 환경에서 충돌 방지를 위해 3306 대신 다른 포트를 사용해도 됩니다.
MYSQL_PORT=3306
# MySQL 연결 정보
# 보안을 위해 비밀번호는 변경하여 사용하는 것을 권장합니다.
MYSQL_DATABASE=stcs_db
MYSQL_ROOT_PASSWORD=rootpassword
MYSQL_USER=stcs_user
MYSQL_PASSWORD=stcs_password
MYSQL_GUEST_USER=guestUser
MYSQL_GUEST_PASSWORD=GuestPassword000!
DB_HOST=localhost
```

---

### 🚀 실행 방법

#### **사전 요구 사항**

- Docker & Docker Compose
- **Data Initialization:** `src/main/resources/data.sql` 위치에 교재의
  `lareRelationsInsertFile.sql` 내용이 포함되어 있어야 서버 실행 시 데이터가 적재됩니다.

#### 전체 시스템 실행

- 애플리케이션과 데이터베이스를 포함한 전체 환경을 실행합니다.

```shell
# 프로젝트 루트에서 실행
docker-compose up --build -d
```

#### 개발용 DB만 실행

- 로컬 개발 시 데이터베이스(MySQL)만 독립적으로 실행할 때 사용합니다.

```shell
# 개발용 MySQL 컨테이너 실행
docker-compose -f docker-compose-dev.yml up -d db-seminar
```

---

## 🗄️ 데이터베이스 스키마 (ERD)

**Korth의 University Database** 공식 스키마를 기반으로 설계되었습니다.

복합 키(Composite Key)와 순환 참조 관계를 명확히 시각화하기 위해 DBML을 사용하여 모델링하였습니다.

![ER Diagram](docs/images/stcs_db_seminar_erd.png)
[DB ERD 다운로드(PDF)](docs/stcs_db_seminar_erd.pdf)

<details>
<summary><b>📂 DBML 스크립트 보기</b></summary>
<br>

```groovy
Table classroom {
  building VARCHAR(15) [primary key, note: "건물명 (복합 PK)"]
  room_number VARCHAR(7) [primary key, note: "강의실 번호 (복합 PK)"]
  capacity DECIMAL(4, 0) [note: "수용 인원"]
}

Table department {
  dept_name VARCHAR(20) [primary key, note: "학과명 (PK)"]
  building VARCHAR(15) [note: "건물명"]
  budget DECIMAL(12, 2) [note: "학과 예산 ('CHECK (budget > 0)')"]
}

Table course {
  course_id VARCHAR(8) [primary key, note: "강의 ID (PK)"]
  title VARCHAR(50) [note: "학생명"]
  dept_name VARCHAR(20) [note: "학과명 (FK)"]
  credits DECIMAL(2, 0) [note: "총 이수 학점 ('CHECK (credits > 0)')"]
}

Table instructor {
  instructor_id VARCHAR(5) [primary key, note: "교수 ID (PK)"]
  name VARCHAR(20) [not null, note: "교수명"]
  dept_name VARCHAR(20) [note: "학과명 (FK)"]
  salary DECIMAL(8, 2) [note: "월급 ('CHECK (salary > 29000)')"]
}

Table section {
  course_id VARCHAR(8) [primary key, note: "강의 ID (복합 PK / FK)"]
  sec_id VARCHAR(8) [primary key, note: "분반 ID (복합 PK)"]
  semester VARCHAR(6) [primary key, note: "학기 (복합 PK / CHECK (semester IN ('Fall', 'Winter', 'Spring', 'Summer')"]
  year DECIMAL(4, 0) [primary key, note: "년도 (복합 PK / 'CHECK (year > 1701 AND year < 2100)')"]
  building VARCHAR(15) [note: "건물명 (FK)"]
  room_number VARCHAR(7) [note: "강의실 번호 (FK)"]
  time_slot_id VARCHAR(4) [note: "수업 교시 ID (FK / 논리적 참조)"]
}

Table teaches {
  instructor_id VARCHAR(5) [primary key, note: "교수 ID (복합 PK / FK)"]
  course_id VARCHAR(8) [primary key, note: "강의 ID (복합 PK / FK)"]
  sec_id VARCHAR(8) [primary key, note: "분반 ID (복합 PK / FK)"]
  semester VARCHAR(6) [primary key, note: "학기 (복합 PK / FK)"]
  year DECIMAL(4, 0) [primary key, note: "년도 (복합 PK / FK)"]
}

Table student {
  student_id VARCHAR(5) [primary key, note: "학생 ID (PK)"]
  name VARCHAR(20) [not null, note: "학생명"]
  dept_name VARCHAR(20) [note: "학과명 (FK)"]
  tot_cred DECIMAL(3, 0) [note: "총 이수 학점 ('CHECK (tot_cred >= 0)')"]
}

Table takes {
  student_id VARCHAR(5) [primary key, note: "학생 ID (복합 PK / FK)"]
  course_id VARCHAR(8) [primary key, note: "강의 ID (복합 PK / FK)"]
  sec_id VARCHAR(8) [primary key, note: "분반 ID (복합 PK / FK)"]
  semester VARCHAR(6) [primary key, note: "학기 (복합 PK / FK)"]
  year DECIMAL(4, 0) [primary key, note: "년도 (복합 PK / FK)"]
  grade VARCHAR(2) [note: "성적"]
}

Table advisor {
  student_id VARCHAR(5) [primary key, note: "학생 ID (PK / FK)"]
  instructor_id VARCHAR(5) [note: "교수 ID (FK)"]
}

Table time_slot {
  time_slot_id VARCHAR(4) [primary key, note: "수업 교시 ID (복합 PK)"]
  day VARCHAR(1) [primary key, note: "요일 (복합 PK)"]
  start_hr DECIMAL(2, 0) [primary key, note: "시작 시간 (복합 PK / 'CHECK (start_hr >= 0 AND start_hr < 24)'"]
  start_min DECIMAL(2, 0) [primary key, note: "시작 분 (복합 PK / 'CHECK (start_min >= 0 AND start_min < 60')"]
  end_hr DECIMAL(2, 0) [note: "종료 시간 ('CHECK (end_hr >= 0 AND end_hr < 24)')"]
  end_min DECIMAL(2, 0) [note: "종료 분 ('CHECK (end_min >= 0 AND end_min < 60)')"]
}

Table prereq {
  course_id VARCHAR(8) [primary key, note: "강의 ID (복합 PK / FK)"]
  prereq_id VARCHAR(8) [primary key, note: "선수 과목 ID (복합 PK / FK)"]
}

Ref: course.dept_name > department.dept_name
Ref: instructor.dept_name > department.dept_name
Ref: section.course_id > course.course_id
Ref: section.(building, room_number) > classroom.(building, room_number)
Ref: teaches.(course_id, sec_id, semester, year) > section.(course_id, sec_id, semester, year)
Ref: teaches.instructor_id > instructor.instructor_id
Ref: student.dept_name > department.dept_name
Ref: takes.student_id > student.student_id
Ref: takes.(course_id, sec_id, semester, year) > section.(course_id, sec_id, semester, year)
Ref: advisor.student_id > student.student_id
Ref: advisor.instructor_id > instructor.instructor_id
Ref: prereq.course_id > course.course_id
Ref: prereq.prereq_id > course.course_id
```

</details>

---

## <img src="docs/images/sailorham_logo.png" height="24" align="left">작성자<br clear="left"/>

- **이름:** 함상현
- **학과:** 서울신학대학교 일반대학원 IT융합학과
- **이메일:** hhak3504@stu.ac.kr
