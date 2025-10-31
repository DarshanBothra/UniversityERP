CREATE TABLE users_auth(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    role ENUM('ADMIN', 'INSTRUCTOR', 'STUDENT') NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'INACTIVE',
    last_login TIMESTAMP DEFAULT NULL
    );


INSERT INTO users_auth (username, role, password_hash) VALUES
('admin1', 'ADMIN', '$2a$12$laxNOG915ok2sqfL.PA0Fed.4vIreE3TR19ZbJBTOpg58Z6KPNdnO'),
('inst1', 'INSTRUCTOR', '$2a$12$RNMFwkhxipbsazLJbgzZiua/FS0WebyUdzuQflrSIRRDl4g9i5X0a'),
('stu1', 'STUDENT', '$2a$12$3mfbviAn57XkY/uR17bK9utFiSG2GsCAgmJTDifqHrc8092exVuUq'),
('stu2', 'STUDENT', '$2a$12$ldpgVKOvUCCpYak9jlXEqO/O0.AzhmT.WPK8cntomRYzSUEI6SGn2');