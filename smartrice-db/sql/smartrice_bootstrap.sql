-- SmartRice Platform Bootstrap
-- Run once with a privileged MySQL account when provisioning a brand new environment.

CREATE DATABASE IF NOT EXISTS smartrice DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE USER IF NOT EXISTS 'smartrice'@'localhost' IDENTIFIED BY 'smartrice123456';
GRANT ALL PRIVILEGES ON smartrice.* TO 'smartrice'@'localhost';
FLUSH PRIVILEGES;
