CREATE DATABASE IF NOT EXISTS __DB_NAME__ ;
USE __DB_NAME__;
create table IF NOT EXISTS __TABLE_NAME__ (
   customer_id INT NOT NULL AUTO_INCREMENT,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   borrow_amount DECIMAL(8,2) NOT NULL,
   phone VARCHAR(15) NOT NULL,
   email VARCHAR(100) NOT NULL,
   street VARCHAR(100) NOT NULL,
   city VARCHAR(50) NOT NULL,
   state VARCHAR(25) NOT NULL,
   zip_code VARCHAR(15) NOT NULL,

   message TEXT,
   creation_at DATE,
   modified_at DATE,

   PRIMARY KEY ( customer_id )
);