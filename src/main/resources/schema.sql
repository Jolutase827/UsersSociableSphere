CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    photo VARCHAR(255),
    description CLOB,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    wallet DECIMAL(10, 2),
    api_token VARCHAR(255) UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
