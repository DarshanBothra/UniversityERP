# University ERP System

A Java + Swing Desktop Application with Two-Database Authentication

## Overview

The University ERP System is a desktop application designed to manage a university’s academic workflow courses, sections, enrollments, grades while enforcing strict role-based access and dual-database security.

It supports three user roles:

 - **Student**: Browse catalog, register/drop, view timetable, view grades, download transcript

 - **Instructor**: View assigned sections, enter grades, compute finals, see stats

 - **Admin**: Manage users, courses, sections, maintenance mode, backups

The application is built using:

 - Java JDK 24 (or ≥17)

 - Swing for UI

 - JDBC for MySQL communication

 - jBCrypt for password hashing

Layered Architecture: UI → API → Service → DAO → DB

## Project Structure
```
UniversityERP/
│
├── config/
│   └── db_config.properties        # Database Credentials (Stored Separately)
│
├── lib/                            # External JARs/ Libraries used (mysql-connector, jbcrypt, etc.)
│
├── run/
│   └── run.sh                      # Build and run script for the project
│  
├── db/              
│   ├── erp_db.sql # Setup file for erp db with dummy data
│   ├── auth_db.sql # Setup file for auth db with dummy data
│   └── setup.sql # Main setup file to source
│
├── src/
│   └── main/java/
│       └── edu/univ/erp/
│           ├── ui/                 # Swing UI (Login, Dashboards, Panels)
│           │   └── Main.java       # Entry point
│           │
│           ├── api/                # API layer (UI → Service)
│           │   ├── StudentAPI.java
│           │   ├── InstructorAPI.java
│           │   ├── AdminAPI.java
│           │
│           ├── service/            # Business logic
│           │   ├── StudentService.java
│           │   ├── InstructorService.java
│           │   └── AdminService.java
│           │
│           ├── access/             # Access control and Maintenance
│           │   ├── AccessControl.java
│           │   └── MaintenanceManager.java
│           │
│           ├── auth/               # Login session and hashing
│           │   ├── AuthService.java
│           │   ├── hash/PasswordUtility.java
│           │   ├── session/SessionManager.java
│           │   └── store/AuthStore.java
│           │
│           ├── data/               # DAOs for ERP DB and Auth DB
│           │   ├── DBConnection.java
│           │   ├── StudentDAO.java
│           │   ├── InstructorDAO.java
│           │   ├── CourseDAO.java
│           │   ├── SectionDAO.java
│           │   ├── EnrollmentDAO.java
│           │   ├── GradeDAO.java
│           │   └── SettingDAO.java
│           │
│           ├── domain/            
│           │   ├── Student.java
│           │   ├── Instructor.java
│           │   ├── Course.java
│           │   ├── Section.java
│           │   ├── Enrollment.java
│           │   ├── Grade.java
│           │   ├── User.java
│           │   ├── SectionDetail.java # Enum
│           │   ├── Component.java # Enum
│           │   ├── LoginStatus.java # Enum
│           │   ├── Program.java # Enum
│           │   ├── Role.java # Enum
│           │   └── Status.java # Enum
│           │
│           └── util/
│               ├── ValidationUtility.java
│                ExportUtility.java    # CSV/PDF and DB backup
├── ERDiagram.png # ER Diagram for databases and tables   
├── UML.png # UML For Project      
└── TestingPlan.pdf # Testing Plan for the Project

```

## Authentication & Password Security (BCrypt)
- Passwords stored only in Auth DB

- Passwords are never stored in the ERP DB, meeting project requirements.

- BCrypt Hashing using jBCrypt (Cost Factor: 10)

PasswordUtility.java class supports:

hashPassword(plain): to generate bcrypt password hash

checkPassword(plain, hash): match hash with plain password string

isStrongPassword(): check if password is strong

### Login Flow

1. UI sends username and password → AuthAPI

2. AuthService fetches hash from AuthStore

3. BCrypt verifies

4. On success:
→ SessionUser created
→ lastLogin & status updated

5. ERP profile is loaded based on user_id

## Layer Responsibilities

### UI Layer

- Swing panels, dialogs, tables, forms.
Does not talk to DB or Service directly.

### API Layer

- Acts like "controller" or "middleman".
Example: StudentAPI.register() calls StudentService.registerSection().

### Service Layer

- Business rules and access rules check, maintenance check

### Data Layer (Data Access Objects)

- Raw SQL queries only.

### Auth Layer

- Login
- logout
- sessions
- hashing.

### Access Layer

- Checks: "Is user allowed?"

- Maintenance mode ON/OFF (blocks writes)

### Util Layer

- CSV/PDF exports

- Validation

- Backup/restore

## Testing Plan (Required by Project Document)
- Elaborate Testing plan with test results provided in root project directory : "TestingPlan.pdf"
- Demo Video provided in root project directory: "Demo.mp4"

## Test Dataset

setup.sql — creates both databases, tables, and inserts sample admin1, inst1, stu1, stu2.

(Confirmed by the “Testing Dataset” section of the PDF.)


## How to Compile & Run (MacOS)
Prerequisites

- Java 21 (or 17+)

- MySQL running locally

- setup.sql executed with sample data

### Compile and Run

From the root folder:

```bash
cd UniversityERP
cd run
chmod +x run.sh
./run.sh
```

#### run.sh Script (Already created)
```bash

cd ..
mysql < setup.sql

javac -cp "./lib/*:./src" -d out $(find src/main/java -name "*.java")

java -cp "./out:./lib/*" edu.univ.erp.ui.Main
```

Run using:

```bash
chmod +x run.sh
./run/run.sh
```