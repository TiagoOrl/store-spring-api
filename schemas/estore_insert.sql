use estore;

-- Inserir Clientes

INSERT INTO client (
	first_name, second_name, country_id, email, dob
)
VALUES (
	'Jeff', 'Moraes', '156.265.223-33', 'jeffmoraes23@mail.com', '1998-05-10'
);

INSERT INTO client (
	first_name, second_name, country_id, email, dob
)
VALUES (
	'Robinson', 'Abraao', '456.321.562-11', 'robinson_abraao@mail.com', '1980-10-28'
);

INSERT INTO client (
	first_name, second_name, country_id, email, dob
)
VALUES (
	'Rita', 'Da Silva', '111.222.333-45', 'rita_silva432@gmail.com', '1975-12-03'
);

INSERT INTO client (
	first_name, second_name, country_id, email, dob
)
VALUES (
	'Maria', 'Rodrigal', '000.451.652-35', 'maria_rodrigal@mail.com', '1997-05-23'
);


INSERT INTO client (
	first_name, second_name, country_id, email, dob
)
VALUES (
	'Cassio', 'Goleiro', '195.165.556-02', 'goleiro_cassio@outlook.com', '1991-08-19'
);



-- Inserir Endereços dos clientes

INSERT INTO address (
	street, neighborhood, number, city, state_or_county, country, fk_client_id
)
VALUES (
	'Rua Basco', 'Vila ABCDE', 13, 'Campanis', 'Minas Gerais', 'Brasil', 21
);

INSERT INTO address (
	street, neighborhood, number, city, state_or_county, country, fk_client_id
)
VALUES (
	'Rua ABC', 'Vila ABCDE', 48, 'Sao Joao', 'Amazonas', 'Brasil', 3
);

INSERT INTO address (
	street, neighborhood, number, city, state_or_county, country, fk_client_id
)
VALUES (
	'Rua Valdeverde Um', 'Sao Marsds', 12, 'Curitiba', 'Parana', 'Brasil', 1
);



-- Inserir Categorias de produtos

INSERT INTO product_category (
	name
)
VALUES (
	'videogame'
);

INSERT INTO product_category (
	name
)
VALUES (
	'celular'
);

INSERT INTO product_category (
	name
)
VALUES (
	'informatica'
);

INSERT INTO product_category (
	name
)
VALUES (
	'escritório'
);

INSERT INTO product_category (
	name
)
VALUES (
	'eletrodomésticos'
);

INSERT INTO product_category (
	name
)
VALUES (
	'livraria'
);



-- Adding products

INSERT INTO product (
	name, description, unit_price, units_in_stock, fk_category_id
)
VALUES (
	'Xbox Series X', 'Console Microsoft com 3 Jogos!!!', 4500.99, 480, 1
);

INSERT INTO product (
	name, description, unit_price, units_in_stock, fk_category_id
)
VALUES (
	'Nintendo Switch OLED', 'Cor Preto, com 2 jogos físicos, Super Mario edition', 2100.45, 350, 1
);



INSERT INTO product (
	name, description, unit_price, units_in_stock, fk_category_id
)
VALUES (
	'Caderno 10 Matérias', 'Cor: Azul, com adesivos', 15.40, 1300, 6
);

INSERT INTO product (
	name, description, unit_price, units_in_stock, fk_category_id
)
VALUES (
	'Caderno 15 Matérias', 'Cor: Verde, com adesivos', 18.40, 800, 6
);

INSERT INTO product (
	name, description, unit_price, units_in_stock, fk_category_id
)
VALUES (
	'Lapiseira Pentel S1', '0.7mm, cor cinza, com borracha', 20.40, 500, 6
);


INSERT INTO product (
	name, description, unit_price, units_in_stock, fk_category_id
)
VALUES (
	'Cadeira Executivo Falafel', 'Feita em couro sintético', 800, 20, 4
);

INSERT INTO product (
	name, description, unit_price, units_in_stock, fk_category_id
)
VALUES (
	'Cadeira Secretária Falafel Z ', 'Feita em couro sintético', 480.99, 30, 4
);


