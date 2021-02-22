CREATE DATABASE IF NOT EXISTS critter;

CREATE USER IF NOT EXISTS 'critter_user'@'localhost' IDENTIFIED BY 'critter123';

GRANT ALL ON critter.* TO 'critter_user'@'localhost';