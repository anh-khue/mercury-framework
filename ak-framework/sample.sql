# Create Databases and Tables
CREATE DATABASE IF NOT EXISTS framework_test_db;

USE framework_test_db;

CREATE TABLE IF NOT EXISTS drinks (
  id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  drink_name   VARCHAR(50) UNIQUE NOT NULL,
  price        DOUBLE UNSIGNED,
  created_date TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  deleted_date TIMESTAMP          NULL
);


# Initialize sample data
INSERT INTO drinks (drink_name, price) VALUES ('Black Coffee', 18);
INSERT INTO drinks (drink_name, price) VALUES ('Milk Coffee', 20);
INSERT INTO drinks (drink_name, price) VALUES ('Espresso', 22);
INSERT INTO drinks (drink_name, price) VALUES ('Americano', 25);
INSERT INTO drinks (drink_name, price) VALUES ('Cappuccino', 30);
INSERT INTO drinks (drink_name, price) VALUES ('Latte', 30);
