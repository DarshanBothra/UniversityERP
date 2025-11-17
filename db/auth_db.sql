CREATE TABLE users_auth(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    role ENUM('ADMIN', 'INSTRUCTOR', 'STUDENT') NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'INACTIVE',
    last_login TIMESTAMP DEFAULT NULL,
    failed_attempts INT DEFAULT 0
    );


INSERT INTO users_auth (username, role, password_hash) VALUES
('admin1', 'ADMIN', '$2a$10$1gmaS91lL.kL/RdSGjF74.rkmpZdwgjEYGz4vZF1TIBz15shjQzru'),
('inst1', 'INSTRUCTOR', '$2a$10$WQdRjr85hAgbjyApOfWA1uobUIuFLgHQbN/ckvmxytsbCv/6PWLSS'),
('stu1', 'STUDENT', '$2a$10$YxTOzDYnQ8KX75Ev85oKz.qusfd2v/Wx5xh7nT/QklDI6oS.riSXi'),
('stu2', 'STUDENT', '$2a$10$lEsewOWwliWmntXnh8BtseINrBYbS3k/LW5R5X4xUpU7uGmJImuF6');