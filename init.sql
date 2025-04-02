-- Configuración de caracteres
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- Creación y uso de la base de datos
DROP DATABASE IF EXISTS wherehouse;
CREATE DATABASE wherehouse CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE wherehouse;

-- Aseguramos que el usuario root pueda conectarse desde cualquier host
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY '1123';
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '1123';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

-- Crear tablas necesarias
CREATE TABLE IF NOT EXISTS app_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uid VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES app_user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Insertar datos iniciales con el UID correcto
INSERT INTO app_user (id, uid) VALUES (1, 'BjEFr6LYCac9USQZ0Gdp343KjoD2');
INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;