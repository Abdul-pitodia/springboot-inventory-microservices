# **SpringBoot Inventory Management Microservices App** 

This project is a Spring Boot Microservices Application that is a basic inventory management system. The system has 3 main microservices, order, product and inventory service. 
It is a simple project where products can be onboarded in the product microservice, orders can be placed if the item is in stock of the inventory.
The project uses event driven architecture using Apache Kafka to communicate events amongst microservices
It also has a simple email setup where once an order is placed an email is sent after order is placed by user.

## **Technologies Used**
- Spring Boot as the backend
- Spring Cloud Netflix Eureka for Discovery service
- Spring Cloud Netflix Eureka Client for microservice registration
- Spring Circuit breaker
- Apache Kafka for event messaging
- Spring WebClient for inter microservice communication


