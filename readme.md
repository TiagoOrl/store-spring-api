 Serviço de API REST implementada em Java Spring, usando 
Spring MVC, Validator para validação de requisições, ModelMapper para mapeamento de 
 DTOs e entidades, Lombok para auto geração de getters, setters, constructors, Redis para
 caching, MySQL como principal BD, JPA como ORM, Spring Security e JWT para 
 autenticação e autorização de usuários/requests STATELESS.
 

Esta API é para uma loja de produtos eletronicos, onde o usuário consegue criar produtos,
categorias, novos usuários, endereços, criar pedidos, adicionar produtos a um pedido, 
editar a quantidade de um produto em um pedido, e realizar diversas alterações e consultas.



## Instalar Docker

### Add Docker's official GPG key:
sudo apt-get update \
sudo apt-get install ca-certificates curl \
sudo install -m 0755 -d /etc/apt/keyrings \
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc \
sudo chmod a+r /etc/apt/keyrings/docker.asc 

### Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null \
sudo apt-get update


sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin \
sudo docker run hello-world


### mysql
docker run --name mysql_instance -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin --cpus=1.5 -d mysql:8.0-debian

### RabbitMQ
docker run -d --name rabbitMQ -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin  rabbitmq:3-management


### REDIS
docker run -d --name redis-stack-server -p 6379:6379 redis/redis-stack-server:latest