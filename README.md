# Kafka Test 🚀

Este proyecto es una prueba de concepto para implementar **Apache Kafka** con **Java y Spring Boot**, simulando un flujo de pedidos y facturación a través de microservicios independientes.

## 📁 Estructura del proyecto

kafka-test/
├── consumer_facturacion # Servicio consumidor que procesa mensajes de facturación
├── producer_pedido # Servicio productor que envía pedidos a Kafka
├── .gitignore
└── LICENSE


## 🛠️ Tecnologías utilizadas

- Java 17+
- Spring Boot
- Apache Kafka
- Docker (recomendado para levantar Kafka localmente)
- Maven

## ⚙️ Requisitos

- Java 17+
- Apache Maven
- Docker + Docker Compose (para Kafka)

## ▶️ Cómo ejecutar el proyecto

### 1. Levantar Kafka con Docker

Puedes usar un archivo `docker-compose.yml` como este (colócalo en la raíz del proyecto):

```yaml
version: '2'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

  kafka:
    image: confluentinc/cp-kafka:latest
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

docker-compose up -d

2. Ejecutar el productor (Pedidos)
cd producer_pedido
mvn spring-boot:run

3. Ejecutar el consumidor (Facturación)
cd consumer_facturacion
mvn spring-boot:run

📬 Flujo de trabajo
El servicio producer_pedido expone una API REST (por ejemplo, /pedidos) para enviar pedidos.

Al recibir una petición, publica un mensaje en un tópico de Kafka.

El servicio consumer_facturacion escucha ese tópico y procesa el mensaje.

📮 Ejemplo de request al productor

POST http://localhost:8081/pedidos
Content-Type: application/json

{
  "productoId": "abc123",
  "cantidad": 5,
  "clienteEmail": "cliente@ejemplo.com"
}

