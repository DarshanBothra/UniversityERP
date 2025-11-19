CREATE USER 'erpuser'@'localhost' IDENTIFIED BY 'ERP@2025#';

GRANT ALL PRIVILEGES ON erp_db.* TO 'erpuser'@'localhost';
GRANT ALL PRIVILEGES ON erp_db.* TO 'erpuser'@'localhost';

FLUSH PRIVILEGES;

SOURCE /Volumes/Clone_1TB/College/AP/Project/UniversityERP/db/erp_db.sql
SOURCE /Volumes/Clone_1TB/College/AP/Project/UniversityERP/db/auth_db.sql
