Employee CRUD Application

A simple Spring Boot application to manage employees with full CRUD operations, pagination, sorting, and Swagger documentation.

## Features
- Add new employee
- Get all employees
- Get employee by ID
- Update employee
- Delete employee
- Pagination and sorting
- Input validation
- Exception handling
- Swagger API documentation

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Swagger (springdoc-openapi)
- JUnit 5, Mockito

## How to Run

1. **Clone the project**
git clone https://github.com/PratikDurade/employee-crud-application.git
2. **Configure MySQL** in `application.properties`
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

3. **Run the application**

4. **Access API Docs**  
Open in browser:  http://localhost:9090/swagger-ui.html

## API Endpoints

- `POST /employee/addEmployee`
- `GET /employee/getAllEmployees`
- `GET /employee/page/employees?page=0&size=5`
- `GET /employee/getAllEmployeesSorted?sortBy=name&sortDir=asc`
- `GET /employee/{id}`
- `PUT /employee/update/{id}`
- `DELETE /employee/delete/{id}`

## Author
**Pratik Durade**  
GitHub: [PratikDurade](https://github.com/PratikDurade)














