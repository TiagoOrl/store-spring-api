curl --location 'localhost:8080/api/product/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Notebook Ideapad 3",
    "description": "Core i5 8GB RAM 1TB HDD",
    "unitPrice": 2800.99,
    "unitsInStock": 30,
    "categoryId": 1,
    "active": true
}'

curl --location 'localhost:8080/api/product/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "PC Gamer RTX 3060",
    "description": "Ryzen 5, 16GB RAM, 1TB SSD, RTX 3060",
    "unitPrice": 4850.00,
    "unitsInStock": 10,
    "categoryId": 1,
    "active": true
}'

curl --location 'localhost:8080/api/product/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Kit Caneta BIC",
    "description": "Cores Preto, Verde e Vermelho",
    "unitPrice": 4.10,
    "unitsInStock": 80,
    "categoryId": 2,
    "active": true
}'

curl --location 'localhost:8080/api/product/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Caneta BIC Azul",
    "description": "Ponta fina",
    "unitPrice": 2.50,
    "unitsInStock": 90,
    "categoryId": 2,
    "active": true
}'

curl --location 'localhost:8080/api/product/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Caneta BIC Preto",
    "description": "Ponta fina",
    "unitPrice": 2.50,
    "unitsInStock": 90,
    "categoryId": 2,
    "active": true
}'

curl --location 'localhost:8080/api/product/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Papel A4 pacote 150",
    "description": "150 unidades cada",
    "unitPrice": 12.50,
    "unitsInStock": 40,
    "categoryId": 2,
    "active": true
}'

curl --location 'localhost:8080/api/product/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Papel A4 pacote 300",
    "description": "300 unidades cada",
    "unitPrice": 19.00,
    "unitsInStock": 50,
    "categoryId": 2,
    "active": true
}'


curl --location 'localhost:8080/api/product/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Batedeira ARNO AM",
    "description": "750W",
    "unitPrice": 260.00,
    "unitsInStock": 20,
    "categoryId": 3,
    "active": true
}'

curl --location 'localhost:8080/api/product/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Liquidificador ARNO Ultra MAX",
    "description": "850W, com kit filtro",
    "unitPrice": 310.00,
    "unitsInStock": 30,
    "categoryId": 3,
    "active": true
}'




