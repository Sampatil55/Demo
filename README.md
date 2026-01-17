# Demo
1. Prerequisites

Make sure the following are installed on your system:

Java JDK 17 (recommended for Spring Boot 3)

Maven 3.8+

Spring Boot 3.x

MySQL Server 8.x

MySQL Workbench

Postman

IDE (IntelliJ IDEA / Eclipse / VS Code)

OpenAI API Key

2. springboot-openai-project
│── src/main/java
│ └── com.example.demo
│ ├── controller
│ ├── service
│ ├── repository
│ ├── entity
│ └── config
│── src/main/resources
│ ├── application.properties
│ └── application.yml (optional)
│── pom.xml
│── README.md

3. Create Database

4. Configure application.properties

5.OpenAI API Configuration
5.1 Add Dependency (pom.xml)
5.2 Store OpenAI API Key

6. Testing APIs with Postman
6.1 7.1 Example Request

Method: POST

URL: http://localhost:8080/api/openai/chat

Headers:

Content-Type: application/json

7.Running the Application

Start MySQL Server

Open project in IDE

Update application.properties

Run:

