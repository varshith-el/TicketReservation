# Movie Reservation System

## Overview
The **Movie Reservation System** is a Spring Boot application designed to manage movie showtimes, reservations, and seat bookings. It provides a backend for a movie theater system, allowing users to browse movies, view showtimes, and reserve seats, while administrators can manage movie data. The application uses Spring Data JPA for database operations, Redis for handling concurrent users, and follows a RESTful architecture.

## Features
- **Movie Management**: Store, retrieve, create, update, and delete movie details (title, description, genre, poster URL, duration) with admin CRUD operations.
- **Showtime Management**: Schedule and manage showtimes for movies, including theater details and pricing.
- **Seat Management**: Automatically initialize seats for each showtime and track reservations.
- **Search Functionality**: Find movies by genre, title, or showtimes for the current day.
- **Concurrency Handling**: Use Redis to manage concurrent user access and prevent race conditions during seat reservations.

## Technologies Used
- **Java**: Backend programming language.
- **Spring Boot**: Framework for building the application.
- **Spring Data JPA**: For database operations and repository management.
- **Redis**: For managing concurrent users and caching.
- **MySQL**: Database for storing movie, showtime, and reservation data (configurable for other databases like H2 or PostgreSQL).
- **Hibernate**: ORM for mapping entities to database tables.
- **Maven**: Dependency management and build tool.

## Project Structure
The project follows a standard Spring Boot structure with key components:

- **Entities**:
  - `MovieEntity`: Represents a movie with attributes like title, description, genre, duration, and showtimes.
  - `ShowTime`: Represents a showtime with details like movie, date/time, price, theater name, and seats.
  - `SeatEntity`: Represents individual seats for a showtime.
  - `ReservationEntity`: Tracks seat reservations for a showtime.

- **Repositories**:
  - `MovieRepository`: JPA repository for movie-related queries, including finding movies by genre, title, or today's showtimes.
  - `ShowTimeRepository`: JPA repository for showtime-related queries, such as finding showtimes by movie, date, or time range.
  - `UserRepository`: JPA repository for user related queries.

- **Services**:
  - `MovieService`: Business logic for retrieving and managing movie data, including today's showtimes and admin CRUD operations.

- **Redis Integration**:
  - Redis is used to manage concurrent user access, ensuring thread-safe seat reservations and preventing overbooking.

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL (or another compatible database)
- Redis server
- IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd movie-reservation
   ```

2. **Configure the Database**:
   - Create a MySQL database named `moviereservation`.
   - Update the `application.properties` file in `src/main/resources` with your database and Redis credentials:
     ```properties
     spring.datasource.url=jdbc:jdbc:h2:mem:moviedb
     spring.datasource.username=user
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
     spring.data.redis.host=localhost
     spring.data.redis.port=6379
     ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

## Usage
- **API Endpoints**:
  - **User APIs**:
    - `GET /api/movies`: Retrieve all movies.
    - `GET /api/movies/today`: Retrieve movies with showtimes for today.
    - `GET /api/movies?genre={genre}`: Filter movies by genre.
    - `GET /api/showtimes?movieId={id}&date={date}`: Retrieve showtimes for a specific movie and date.
    - `POST /api/reservations`: Create a reservation.
    - `GET /api/reservations/my-reservations`: Retrieve all user reservations.
    - `DELETE /api/reservations/{id}`: Delete a reservation.
    - `GET /api/reservations/seats/{showtimeId}`: Retrieve available and unavailable seats of a showtime.
  - **Admin APIs (CRUD for Movies)**:
    - `POST /api/admin/movies`: Create a new movie (requires admin authentication).
    - `PUT /api/admin/movies/{id}`: Update an existing movie by ID.
    - `DELETE /api/admin/movies/{id}`: Delete a movie by ID.
    - `GET /api/admin/movies`: Retrieve all movies (admin view, may include additional details).


- **Redis Usage**:
  - Redis is used to manage concurrent seat reservations, ensuring atomic operations during booking.
  - Example: Use Redis locks or atomic counters to prevent multiple users from reserving the same seat simultaneously.

## Database Schema
The application automatically generates the database schema based on the entities using `spring.jpa.hibernate.ddl-auto=update`. Key tables include:
- `movies`: Stores movie details (id, title, description, poster_url, genre, duration_minutes, created_at).
- `showtimes`: Stores showtime details (id, movie_id, show_datetime, price, theater_name, total_seats, created_at).
- `seats`: Stores seat details for each showtime.
- `reservations`: Stores reservation details.