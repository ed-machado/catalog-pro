# Product Catalog System
A full-stack web application for managing products and categories with authentication and authorization.

## 🚀 Features
- **User authentication and authorization** with JWT
- **Product management** (CRUD operations)
- **Category management** (CRUD operations)
- **Responsive design** with dark mode support
- **Docker containerization** for easy deployment

## 🛠️ Technologies Used

### Backend
- **Java 17**
- **Spring Boot 3.3.5**
- **Spring Security** with JWT
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**
- **Lombok**

### Frontend
- **Angular 18**
- **TypeScript**
- **SCSS**
- **MDB Angular UI Kit**
- **JWT Decode**
- **SweetAlert2**
- **Chart.js**
- **Font Awesome**

### DevOps
- **Docker**
- **Docker Compose**
- **Nginx**

## 📋 Prerequisites
- **Docker** and **Docker Compose**
- **Java 17** (for local development)
- **Node.js 18+** (for local development)
- **MySQL 8.0** (for local development)

## 🚀 Running the Application

### Clone the repository:
```bash
git clone https://github.com/ed-machado/catalog-pro/
cd catalog-pro/catalog-prod
mvn clean install -DskipTests
cd ..
```

### Run with Docker Compose:
```bash
docker-compose up --build
```

The development server will be available at [http://localhost:4200](http://localhost:4200).

### Running Locally for Development

#### Backend:
```bash
cd catalog-prod
mvn clean install
mvn spring-boot:run
```

#### Frontend:
```bash
cd catalog-client
npm install
ng serve
```

## 📁 Project Structure

```
.
├── catalog-client/         # Angular frontend application
├── catalog-prod/           # Spring Boot backend application
└── docker-compose.yml      # Docker Compose configuration
```

### catalog-client/
```
├── src/
│   ├── app/
│   │   ├── components/    # Angular components
│   │   ├── models/        # Data models
│   │   ├── services/      # API services
│   │   └── auth/          # Authentication
│   └── assets/            # Static files
```

### catalog-prod/
```
├── src/
│   └── main/
│       ├── java/
│       │   └── com/machado/catalog_prod/
│       │       ├── auth/        # Login "DTO"
│       │       ├── config/      # Configuration classes
│       │       ├── controller/  # REST controllers
│       │       ├── dto/         # Data transfer objects
│       │       ├── entity/      # JPA entities
│       │       ├── exception/   # Custom exceptions and handlers
│       │       ├── repository/  # Data repositories
│       │       └── service/     # Business logic
│       │       └── util/        # Utility classes
│       └── resources/
│           └── application.properties
```

## 🔒 Security
The application implements JWT-based authentication with the following features:
- **Token-based authentication**
- **Role-based authorization**
- **Secure password hashing**
- **CORS configuration**
- **Protected API endpoints**

## 📝 API Documentation

### Authentication Endpoints
- `POST /api/login` - Authenticate user
- `POST /api/register` - Register new user

### Product Endpoints
- `GET /product/read/all` - Get all products (paginated)
- `GET /product/read/{id}` - Get product by ID
- `POST /product/create` - Create new product
- `PUT /product/update/{id}` - Update product
- `DELETE /product/delete/{id}` - Delete product

### Category Endpoints
- `GET /category/read/all` - Get all categories (paginated)
- `GET /category/read/{id}` - Get category by ID
- `POST /category/create` - Create new category
- `PUT /category/update/{id}` - Update category
- `DELETE /category/delete/{id}` - Delete category

## 🖥️ Environment Variables
The application uses the following environment variables:

```bash
# spring.datasource.url=jdbc:mysql://localhost:3306/catalog_db LOCAL
spring.datasource.url=jdbc:mysql://mysql-db:3306/catalog_db
spring.datasource.username=root
spring.datasource.password=admin
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.javax.persistence.validation.mode=auto
```

