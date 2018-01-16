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

CREATE TABLE IF NOT EXISTS materials (
  id            INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  material_name VARCHAR(50) UNIQUE NOT NULL,
  created_date  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
  deleted_date  TIMESTAMP          NULL
);

CREATE TABLE IF NOT EXISTS drink_has_material (
  id          INT UNSIGNED AUTO_INCREMENT,
  drink_id    INT UNSIGNED REFERENCES drinks (id),
  material_id INT UNSIGNED REFERENCES materials (id),
  PRIMARY KEY (id, drink_id, material_id)
);

# Initialize sample data

# Drinks
INSERT INTO drinks (drink_name, price) VALUES ('Black Coffee', 18);
INSERT INTO drinks (drink_name, price) VALUES ('Milk Coffee', 20);
INSERT INTO drinks (drink_name, price) VALUES ('Espresso', 22);
INSERT INTO drinks (drink_name, price) VALUES ('Americano', 25);
INSERT INTO drinks (drink_name, price) VALUES ('Cappuccino', 30);
INSERT INTO drinks (drink_name, price) VALUES ('Latte', 30);

# Materials
INSERT INTO materials (material_name) VALUES ('Coffee');
INSERT INTO materials (material_name) VALUES ('Milk');
INSERT INTO materials (material_name) VALUES ('Sugar');
INSERT INTO materials (material_name) VALUES ('Cream');
INSERT INTO materials (material_name) VALUES ('Ice');

# Drink has Meterial
INSERT INTO drink_has_material (drink_id, material_id) VALUES (1, 1);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (1, 5);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (2, 1);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (2, 2);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (2, 3);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (3, 1);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (4, 1);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (4, 5);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (5, 1);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (5, 2);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (5, 4);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (6, 1);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (6, 2);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (6, 4);
INSERT INTO drink_has_material (drink_id, material_id) VALUES (6, 5);
