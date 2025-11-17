DROP DATABASE IF EXISTS erp_db;

CREATE DATABASE erp_db;

USE erp_db;

CREATE TABLE students(
    user_id INT PRIMARY KEY, -- fetch from db/auth_db.users_auth table after creating entry
    student_id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE, -- fetch from db/auth_db.users_auth table after creating entry
    roll_no INT NOT NULL UNIQUE,
    program ENUM('B.Tech', 'M.Tech', 'PhD') NOT NULL,
    current_year INT NOT NULL
);

CREATE TABLE instructors(
    user_id INT PRIMARY KEY, -- fetch from db/auth_db.users_auth table after creating entry
    instructor_id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE, -- fetch from db/auth_db.users_auth table after creating entry
    department VARCHAR(10) NOT NULL
);

CREATE TABLE courses(
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    title VARCHAR(50) NOT NULL,
    credits INT NOT NULL
);

CREATE TABLE sections(
    section_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL, -- query courses table with course code
    instructor_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    day_time VARCHAR(100),
    room VARCHAR(10),
    capacity INT NOT NULL,
    semester VARCHAR(50) NOT NULL,
    current_year INT NOT NULL
);

CREATE TABLE enrollments(
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    section_id INT NOT NULL,
    status ENUM('Enrolled', 'Dropped') DEFAULT 'Enrolled'

);

CREATE TABLE grades(
    grade_id INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id INT NOT NULL,
    component VARCHAR(50) NOT NULL,
    score DOUBLE DEFAULT 0,
    final_grade DOUBLE DEFAULT 0
);

CREATE TABLE settings(
    setting_key VARCHAR(255) PRIMARY KEY,
    setting_value VARCHAR(255)
);

INSERT INTO settings (setting_key, setting_value) VALUES ('maintenance_on', 'off');
INSERT INTO settings (setting_key, setting_value) VALUES ('registration_deadline', '2025-12-31T23:59:59');
INSERT INTO settings (setting_key, setting_value) VALUES ('drop_deadline', '2025-12-31T23:59:59');
