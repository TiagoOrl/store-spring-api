CREATE DATABASE IF NOT EXISTS estore;
USE estore;

-- Definir Cliente

CREATE TABLE IF NOT EXISTS client (
	id int UNSIGNED NOT NULL AUTO_INCREMENT,
	first_name varchar(100) NOT NULL,
	second_name varchar(200) NOT NULL,
	country_id varchar(100) UNIQUE NOT NULL,
	email varchar(100) UNIQUE NOT NULL,
	dob DATE NOT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME(6) DEFAULT NULL,
	PRIMARY KEY (id)
);

-- definir Endereço

CREATE TABLE IF NOT EXISTS address (
	id int UNSIGNED NOT NULL AUTO_INCREMENT,
	street varchar(150) NOT NULL,
	neighborhood varchar(200) NOT NULL,
	number int NOT NULL,
	city varchar(80) NOT NULL,
	state_or_county varchar(100) NOT NULL,
	country varchar(40),
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME(6) DEFAULT NULL,
	fk_client_id INT UNSIGNED UNIQUE NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE address
ADD FOREIGN KEY (fk_client_id) REFERENCES client(id);


-- Definir Categorias de produtos

CREATE TABLE IF NOT EXISTS product_category (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name varchar(100) UNIQUE NOT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME(6) DEFAULT NULL,
	PRIMARY KEY (id)
);



-- Definir Produto

CREATE TABLE IF NOT EXISTS product (
	id int UNSIGNED NOT NULL AUTO_INCREMENT,
	name varchar(60) UNIQUE NOT NULL,
	description TEXT NOT NULL,
	unit_price DECIMAL(13, 2) NOT NULL,
	image_url VARCHAR(255) DEFAULT NULL,
	active BIT DEFAULT 1,
	`units_in_stock` INT(11) DEFAULT NOT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME(6) DEFAULT NULL,
	fk_category_id INT UNSIGNED NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE product
ADD FOREIGN KEY (fk_category_id) REFERENCES product_category(id);


-- Definir Pedido
		 

CREATE TABLE IF NOT EXISTS order_main (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	total_sum DECIMAL(13, 2) NOT NULL,
	fk_client_id INT UNSIGNED NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE order_main
ADD FOREIGN KEY (fk_client_id) REFERENCES client(id);


-- Definir Produto do Pedido


CREATE TABLE IF NOT EXISTS order_product (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	fk_order_id INT UNSIGNED NOT NULL,
	fk_product_id INT UNSIGNED NOT NULL,
	amount INT UNSIGNED NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE order_product
ADD FOREIGN KEY (fk_order_id) REFERENCES order_main(id);

ALTER TABLE order_product
ADD FOREIGN KEY (fk_product_id) REFERENCES product(id);
	
	
	
	
	
	
	
	
	
	
	
