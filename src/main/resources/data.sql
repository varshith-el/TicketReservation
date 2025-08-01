-- Insert sample users (password is BCrypt encoded 'password123')
--INSERT INTO users (name, email, password, role, created_at) VALUES
--('admin', 'admin@movie.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVzkZNOEig6RF7CyGmTFKu', 'ADMIN', CURRENT_TIMESTAMP),
--('john_doe', 'john@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVzkZNOEig6RF7CyGmTFKu', 'USER', CURRENT_TIMESTAMP),
--('jane_smith', 'jane@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFVzkZNOEig6RF7CyGmTFKu', 'USER', CURRENT_TIMESTAMP);

-- Insert sample movies
INSERT INTO movies (id, title, description, poster_url, genre, duration_minutes, created_at) VALUES
(1, 'The Matrix', 'A computer hacker learns about the true nature of reality and his role in the war against its controllers.', 'https://example.com/posters/matrix.jpg', 'SCI_FI', 136, CURRENT_TIMESTAMP),
(2, 'Inception', 'A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea.', 'https://example.com/posters/inception.jpg', 'SCI_FI', 148, CURRENT_TIMESTAMP),
(3, 'The Dark Knight', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests.', 'https://example.com/posters/batman.jpg', 'ACTION', 152, CURRENT_TIMESTAMP),
(4, 'Avengers: Endgame', 'After the devastating events of Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos.', 'https://example.com/posters/endgame.jpg', 'ACTION', 181, CURRENT_TIMESTAMP),
(5, 'Interstellar', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity survival.', 'https://example.com/posters/interstellar.jpg', 'SCI_FI', 169, CURRENT_TIMESTAMP);

-- Insert sample show times
INSERT INTO showtimes (id, movie_id, show_datetime, price, theater_name, total_seats, created_at) VALUES
-- The Matrix shows
(1, 1, '2025-08-01 14:00:00', 12.50, 'Cineplex Downtown', 100, CURRENT_TIMESTAMP),
(2, 1, '2025-08-01 19:30:00', 15.00, 'Cineplex Downtown', 100, CURRENT_TIMESTAMP),
(3, 1, '2025-08-01 16:30:00', 13.50, 'MovieMax Mall', 100, CURRENT_TIMESTAMP),
-- Inception shows
(4, 2, '2025-08-01 15:00:00', 13.00, 'MovieMax Mall', 100, CURRENT_TIMESTAMP),
(5, 2, '2025-08-01 20:00:00', 16.00, 'MovieMax Mall', 100, CURRENT_TIMESTAMP),
(6, 2, '2025-08-01 17:00:00', 14.00, 'Starlight Cinema', 100, CURRENT_TIMESTAMP),
-- The Dark Knight shows
(7, 3, '2025-08-01 18:00:00', 14.50, 'Starlight Cinema', 100, CURRENT_TIMESTAMP),
(8, 3, '2025-08-01 21:30:00', 17.00, 'Starlight Cinema', 100, CURRENT_TIMESTAMP),
(9, 3, '2025-08-01 20:30:00', 16.50, 'Cineplex Downtown', 100, CURRENT_TIMESTAMP),
-- Avengers: Endgame shows
(10, 4, '2025-08-02 15:00:00', 18.00, 'Cineplex Downtown', 100, CURRENT_TIMESTAMP),
(11, 4, '2025-08-02 19:00:00', 18.00, 'MovieMax Mall', 100, CURRENT_TIMESTAMP),
-- Interstellar shows
(12, 5, '2025-08-02 14:30:00', 15.50, 'Starlight Cinema', 100, CURRENT_TIMESTAMP),
(13, 5, '2025-08-02 18:30:00', 17.50, 'Cineplex Downtown', 100, CURRENT_TIMESTAMP);